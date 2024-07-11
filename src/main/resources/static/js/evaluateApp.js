(function() {

	var app = angular.module("evaluateApp", []);

	var evaluateCtrl = function($scope, $http,$parse) {	
		
		$scope.Math = window.Math;
		
		var successStep = ['1', '2.1', '2.2'];
		var answers = [];
		
		$scope.step = 1;
	    $scope.date = new Date();
		$scope.validateSuccess = true;
		$scope.lastStep = 1;
		$scope.initialize = function() {
			if ($scope.assessmentId == 0)
				return;

			$http.get("/api/assessments/" + $scope.assessmentId + "/last-result")
				.then(
					function(response) {
						$scope.result = response.data;
					}, 
					function(reason) {
						$scope.error = "Could not fetch the data.";
					}
				);
		}
		$scope.isNumberKey = function(evt, obj) {
			var theEvent = evt || window.event;
			var key = theEvent.keyCode || theEvent.which;
			key = String.fromCharCode(key);
			var regex = /^[0-9.,]+$/;
			if (!regex.test(key)) {
				theEvent.returnValue = false;
				if (theEvent.preventDefault)
					theEvent.preventDefault();
			}
		
		}
		$scope.back = function() {
			$scope.step = $scope.lastStep;
		}
		$scope.validateCheckbox = function(field){
			var model = $parse('msg_' + field);
			if($scope.$eval('result.' + field) == undefined){
				model.assign($scope, 'Please choose at least one option');
				$scope.validateSuccess = false;
			}else
				model.assign($scope, '');
		}
		$scope.validateText = function(field){
			var model = $parse('msg_' + field);
			if($scope.$eval('result.' + field) == undefined){
				model.assign($scope, 'Please input value');
				$scope.validateSuccess = false;
			}else
				model.assign($scope, '');
		}
		$scope.validateRate = function(field,min, max){
			var model = $parse('msg_' + field);
			var val = $scope.$eval('result.' + field);
			if(val == undefined){
				model.assign($scope, 'Please input value');
				$scope.validateSuccess = false;
			}else if(Number(val) > max || Number(val) < min){
				model.assign($scope, 'Please input value between '+ min + ' and '+ max +'');
				$scope.validateSuccess = false;
			}else
				model.assign($scope, '');
		}
		$scope.next = function() {
			$scope.forceFail=false;
			$scope.validateSuccess = true;
			if($scope.step == 8)
				return;
			var nextStep;
			switch($scope.step){
				case 1 : 
					break;
				case 2 : 
					$scope.validateCheckbox('v_3_1');
					$scope.validateCheckbox('v_3_2_1');
					if(!$scope.validateSuccess)
						return;
					if($scope.result.v_3_2_1 == 0){
						$scope.forceFail=true;
						nextStep = 5;
						$scope.result.v_4=0;
					}
					break;
				case 3 : 
					$scope.validateCheckbox('v_3_2_2');
					$scope.validateCheckbox('v_3_2_3');
					$scope.validateCheckbox('v_3_2_4');
					if(!$scope.validateSuccess)
						return;
					if($scope.result.v_3_2_4 == 0){
						$scope.forceFail=true;
						nextStep = 5;
						$scope.result.v_4=0;
					}
					break;
				case 4 : 
					$scope.validateCheckbox('v_3_3');
					$scope.validateCheckbox('v_3_4');
					if(!$scope.validateSuccess)
						return;
					break;
				case 5 : 
					$scope.validateCheckbox('v_4');
					if(!$scope.validateSuccess)
						return;
					if($scope.result.v_4 == 0)
						nextStep = 8;
					break;
				case 6 : 
					$scope.validateCheckbox('v_4_4');
					if($scope.result.v_4_4 == 2)
						$scope.validateText('v_4_4_2');
					if(!$scope.validateSuccess)
						return;
					break;
				case 7 : 
					if($scope.result.v_4_4 == 0)
						$scope.validateRate('v_4_5_2', 0,3);
					else
						$scope.validateRate('v_4_5_1',0,100);
					$scope.validateRate('v_4_6',20,30);
					if(!$scope.validateSuccess)
						return;
					break;
				default: return;
			}
			$scope.lastStep = $scope.step;
			
			if(nextStep ==undefined && $scope.step < 8)
				$scope.step++;
			else
				$scope.step = nextStep;
			if($scope.step == 8)
				$scope.submitAnswers();
		}

		$scope.submitAnswers = function() {
			if($scope.result.v_4_7 == 'true')
				$scope.result.v_4_7 = 1;
			else
				$scope.result.v_4_7 = 0;
			var xhr = new XMLHttpRequest();
			xhr.open('POST', "/api/assessments/" + $scope.assessmentId + "/submit", true);
			xhr.responseType = 'blob';
			xhr.setRequestHeader("Content-Type", "application/json");
			xhr.onload = function (e) {
			    var blob = e.currentTarget.response;
			    saveAs(blob,"BM02.docx")
			}
			xhr.send(JSON.stringify($scope.result));
		}
	
		$scope.initialize();	
	};

	app.controller("EvaluateCtrl", ["$scope", "$http", "$parse", evaluateCtrl]);

}());