(function() {

	var app = angular.module("evaluateApp", []);

	var evaluateCtrl = function($scope, $http,$parse) {	
		
		$scope.Math = window.Math;
		
		var successStep = ['1', '2.1', '2.2'];
		var answers = [];
		
//		$scope.x = ${user.username};
		$scope.step = 9;
	    $scope.date = new Date();
		$scope.validateSuccess = true;
		$scope.lastStep = 1;
//	    $scope.result ={};
		$scope.initialize = function() {
			if ($scope.assessmentId == 0)
				return;

			$http.get("/api/assessments/" + $scope.assessmentId)
				.then(
					function(response) {
						$scope.assessment = response.data;
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
		$scope.validateRate = function(field){
			var model = $parse('msg_' + field);
			var val = $scope.$eval('result.' + field);
			if(val == undefined){
				model.assign($scope, 'Please input value');
				$scope.validateSuccess = false;
			}else if(Number(val) > 30 || Number(val) < 20){
				model.assign($scope, 'Please input value between 20 and 30');
				$scope.validateSuccess = false;
			}else
				model.assign($scope, '');
		}
		$scope.next = function() {
			$scope.validateSuccess = true;
			if($scope.step == 9)
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
					if($scope.result.v_3_2_1 == 0)
						nextStep = 5;
					break;
				case 3 : 
					$scope.validateCheckbox('v_3_2_2');
					$scope.validateCheckbox('v_3_2_3');
					$scope.validateCheckbox('v_3_2_4');
					if(!$scope.validateSuccess)
						return;
					if($scope.result.v_3_2_4 == 0)
						nextStep = 5;
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
						nextStep = 9;
					break;
				case 6 : 
					$scope.validateCheckbox('v_4_4');
					if(!$scope.validateSuccess)
						return;
					break;
				case 7 : 
					$scope.validateText('v_4_4_2');
					if(!$scope.validateSuccess)
						return;
					break;
				case 8 : 
					$scope.validateText('v_4_5');
					$scope.validateRate('v_4_6');
					if(!$scope.validateSuccess)
						return;
					break;
				default: return;
			}
			$scope.lastStep = $scope.step;
			
			if(nextStep ==undefined && $scope.step < 9)
				$scope.step++;
			else
				$scope.step = nextStep;
			if($scope.step == 9)
				$scope.submitAnswers();
		}

		$scope.submitAnswers = function() {
			$http.post("/api/assessments/" + $scope.assessmentId + "/submit",
					JSON.stringify($scope.result))
			.then(
				function(response) {
					$scope.results = response.data;
					$scope.evaluating = false;
				}, 
				function(reason) {
					$scope.error = "Could not fetch the data.";
				}
			);
		}
	
		$scope.initialize();	
	};

	app.controller("EvaluateCtrl", ["$scope", "$http", "$parse", evaluateCtrl]);

}());