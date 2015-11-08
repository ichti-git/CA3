'use strict';

angular.module('myApp.viewAllUsers', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/viewAllUsers', {
        templateUrl: 'viewAllUsers/viewAllUsers.html',
        controller: 'ViewAllUsers'
    });
}])
.controller('ViewAllUsers', function($http,$scope) {
    $scope.users = [];
    $http.get('api/admin/users')
    .then(function(response) {
        $scope.users = response.data;
    });
    $scope.userdel = null;
    $scope.deleteUser = function(user) {
        $http.delete('api/admin/user/'+user)
        .then(
        function succes(response) {
            $scope.userdel = user;
            for (var i in $scope.users) {
                if ($scope.users[i].userName == user) {
                    $scope.users.splice(i, 1);
                }
            }
        },
        function fail(response) {
            //unused
            var errorMessage = response.data.message;
            var errorCode = response.data.code;
        });
    };
});