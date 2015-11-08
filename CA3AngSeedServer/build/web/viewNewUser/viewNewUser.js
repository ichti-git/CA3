'use strict';

angular.module('myApp.viewNewUser', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/viewNewUser', {
        templateUrl: 'viewNewUser/viewNewUser.html',
        controller: 'ViewNewUserCtrl'
    });
}])
.controller('ViewNewUserCtrl', function($http,$scope) {
    $scope.usersignup = false;
    $scope.usersignupmsg = "";
    $scope.signup = function() {
        var data = '{"userName":"'+$scope.username+'", "password":"'+$scope.password+'"}';
        $http.put('api/make', data)
        .then(function succes(response) {
            $scope.usersignupmsg = response.data.msg;
            $scope.usersignup = true;
            
        })
    };
});