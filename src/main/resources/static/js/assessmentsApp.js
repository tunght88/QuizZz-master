(function() {

	var app = angular.module("assessmentsApp", [])
	.directive('myRepeatDirective', function() {
	  return function(scope, element, attrs) {
	    angular.element(element).css('color','blue');
	    if (scope.$last){
	      var option = {
					    "bPaginate": false,
					    "bLengthChange": false,
					    "bFilter": true,
					    "bInfo": false,
					    "bAutoWidth": false,
					    "searching": false };
	      $('#tab').dataTable(option);	
	    }
	  };
	});

	var assessmentsCtrl = function($scope, $http) {	  
	
		$scope.pagination = {
			pageNumber: 0,
			morePagesAvailable: true
		};
		

		$scope.loadNextPage = function() {
		
			if ($scope.pagination.morePagesAvailable) {
				var x = $http.get("/api/assessments/list?sort=id,desc&page=" + $scope.pagination.pageNumber)
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
		$scope.exportDoc03 = function(assessId) {
			if(assessId == undefined)
				return;
			var xhr = new XMLHttpRequest();
			xhr.open('POST', "/api/assessments/" + assessId+ "/export03", true);
			xhr.responseType = 'blob';
			xhr.setRequestHeader("Content-Type", "application/json");
			xhr.onload = function (e) {
			    var blob = e.currentTarget.response;
			    saveAs(blob,"BM03.docx")
			}
			xhr.send(null);
		}
		$scope.loadNextPage();
		
		
	};

	app.controller("AssessmentsCtrl", ["$scope", "$http", assessmentsCtrl]);

}());