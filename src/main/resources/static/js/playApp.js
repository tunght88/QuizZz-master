(function() {

	var app = angular.module("evaluateApp", []);

	var evaluateCtrl = function($scope, $http) {	
		
		$scope.Math = window.Math;
		
		var successStep = ['1', '2.1', '2.2'];
		var answers = [];
		
		
		$scope.step = 1;
	    $scope.date = new Date();
		

	    $scope.result ={};
		$scope.initialize = function() {
			console.log($scope.assessmentId);
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
		$scope.next = function() {
			$scope.step++;
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

	app.controller("EvaluateCtrl", ["$scope", "$http", evaluateCtrl]);

}());