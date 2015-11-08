describe('myApp.viewExchangeRates ViewExchangeRates', function () {

    var scope, httpBackendMock, ctrl;
    var er = [{currency:{code:"DKK",name:"Danske Kroner"}, rate:4.3}];
    var calc = {result: 504.2};

    beforeEach(module('myApp.viewExchangeRates'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        scope = $rootScope.$new();
        ctrl = $controller('ViewExchangeRates', {$scope: scope});
    }));

    it('Should get exchange rates', function () {
        expect(scope.exchangerates).toEqual({});
        expect(scope.exchangeratesavailable).toEqual(false);
        httpBackendMock.expectGET('api/currency/dailyrates').respond(er);
        //scope.
        httpBackendMock.flush();
        expect(scope.exchangerates[0].currency.code).toEqual("DKK");
        expect(scope.exchangerates[0].rate).toEqual(4.3);
        expect(scope.exchangeratesavailable).toEqual(true);
    });
    
    it('Should calculate exchange', function () {
        httpBackendMock.expectGET('api/currency/dailyrates').respond(er);
        expect(scope.calcResult).toEqual(0);
        expect(scope.calcAmount).toEqual(0);
        expect(scope.calcFrom).toEqual("AUD");
        expect(scope.calcTo).toEqual("AUD");
        scope.calcAmount = 10;
        scope.calcFrom = "DKK";
        httpBackendMock.expectGET('api/currency/calculator/10/DKK/AUD').respond(calc);
        scope.runExchange();
        httpBackendMock.flush();
        expect(scope.calcResult).toEqual(504.2);
    });
    it('Should swap currencies', function () {
        httpBackendMock.expectGET('api/currency/dailyrates').respond(er);
        scope.calcFrom = "DKK";
        scope.calcTo = "EUR";
        scope.swapCurrency();
        expect(scope.calcFrom).toEqual("EUR");
        expect(scope.calcTo).toEqual("DKK");
    });
});

