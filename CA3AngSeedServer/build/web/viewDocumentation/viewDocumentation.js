'use strict';

angular.module('myApp.viewDocumentation', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/viewDocumentation', {
        templateUrl: 'viewDocumentation/viewDocumentation.html',
        controller: 'ViewDocumentation'
    });
}])
.controller('ViewDocumentation', function($scope) {
});