'use strict';

angular.module('myApp.viewExchangeRates', ['ngRoute'])

.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/viewExchangeRates', {
        templateUrl: 'viewExchangeRates/viewExchangeRates.html',
        controller: 'ViewExchangeRates'
    });
}])

.controller('ViewExchangeRates', function ($http, $scope) {
    $scope.exchangerates = {};
    $scope.exchangeratesavailable = false;
    $scope.getExchangeRates = function() {
        var url = 'api/currency/dailyrates';
        $http.get(url)
        .then(function succes(response) {
            $scope.exchangerates = response.data;
            $scope.exchangeratesavailable = true;
        });
        
    };
    $scope.getExchangeRates();
    $scope.calcResult = 0;
    $scope.calcAmount = 0;
    $scope.calcFrom = "AUD";
    $scope.calcTo = "AUD";
    /*
    $scope.runExchange = function() {
        var url = 'api/currency/calculator/'+$scope.calcAmount+'/'+$scope.calcFrom+'/'+$scope.calcTo;
        $http.get(url)
        .then(function succes(response) {
            $scope.calcResult = response.data.result;
        });
    };
    */
    $scope.runExchange = function() {
        if ($scope.exchangeratesavailable) {
            var amount = $scope.calcAmount;
            var fromRate;
            var toRate;
            for (var i in $scope.exchangerates) {
                var er = $scope.exchangerates[i];
                if (er.currency.code == $scope.calcFrom) {
                    fromRate = er.rate;
                }
                if (er.currency.code == $scope.calcTo) {
                    toRate = er.rate;
                }
            }
            $scope.calcResult = amount*fromRate/toRate;
        }
    }
    $scope.swapCurrency = function() {
        var tempTo = $scope.calcTo;
        $scope.calcTo = $scope.calcFrom;
        $scope.calcFrom = tempTo;
        $scope.runExchange();
    };
});