(function() {

	var app = angular.module("homeApp", []);

	var homeCtrl = function($scope, $http) {	  
	
		$scope.pagination = {
			pageNumber: 0,
			morePagesAvailable: true
		};
		
		$scope.loadNextPage = function(quizName, quizDescription) {
		
			if ($scope.pagination.morePagesAvailable) {
				$http.get("/api/assessments?sort=id,desc&page=" + $scope.pagination.pageNumber)
					.then(
						function(response) {
							if ($scope.assessments == undefined) {
								$scope.assessments = response.data.content;
							} else {
								$scope.assessments = $scope.assessments.concat(response.data.content);
							}
							
							$scope.pagination.morePagesAvailable = !response.data.last;
							$scope.pagination.pageNumber++;
						}, 
						function(reason) {
							$scope.error = "Could not fetch the data.";
						}
					);
			}
		};
	
		$scope.loadNextPage();
		
	};

	app.controller("HomeCtrl", ["$scope", "$http", homeCtrl]);

}());