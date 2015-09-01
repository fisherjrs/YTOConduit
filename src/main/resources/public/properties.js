function Hello($scope, $http) {
    //$http.get('http://rest-service.guides.spring.io/greeting').
	$http.get('http://localhost:9000/conduitservices/listproperties').
        success(function(data) {
            $scope.greeting = data;
        });
}