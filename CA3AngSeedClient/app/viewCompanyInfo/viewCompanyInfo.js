'use strict';

angular.module('myApp.viewCompanyInfo', ['ngRoute'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/viewCompanyInfo', {
        templateUrl: 'viewCompanyInfo/viewCompanyInfo.html',
        controller: 'ViewCompanyInfoCtrl'
    });
}])

.controller('ViewCompanyInfoCtrl', function ($http, $scope) {
    $scope.companydata = {};
    $scope.companydataavailable = false;
    $scope.country = "dk";
    $scope.options = "search";
    $scope.searchForCompany = function() {
        var url = 'api/user/cvr/'+ $scope.options +'/'+$scope.search+'/'+$scope.country;
        $http.get(url)
        .then(function succes(response) {
            $scope.companydata = response.data;
            $scope.companydataavailable = true;
        });
        
    };

});