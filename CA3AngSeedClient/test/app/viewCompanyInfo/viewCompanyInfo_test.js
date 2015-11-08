describe('myApp.viewCompanyInfo ViewCompanyInfoCtrl', function () {

    var scope, httpBackendMock, ctrl;
    var company = {name:"company",testdata:"true"};

    beforeEach(module('myApp.viewCompanyInfo'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        scope = $rootScope.$new();
        ctrl = $controller('ViewCompanyInfoCtrl', {$scope: scope});
    }));

    it('Should search for company', function () {
        expect(scope.companydata).toEqual({});
        expect(scope.companydataavailable).toEqual(false);
        httpBackendMock.expectGET('api/user/cvr/search/test/dk').respond(company);
        scope.search = "test";
        scope.searchForCompany();
        httpBackendMock.flush();
        expect(scope.companydata.name).toEqual("company");
        expect(scope.companydata.testdata).toEqual("true");
        expect(scope.companydataavailable).toEqual(true);
    });
});

