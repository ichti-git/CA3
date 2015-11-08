describe('myApp.viewNewUser viewNewUserCtrl', function () {

    var scope, httpBackendMock, ctrl;
    var response = {msg: "You have succesfully signed up"};

    beforeEach(module('myApp.viewNewUser'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        scope = $rootScope.$new();
        ctrl = $controller('ViewNewUserCtrl', {$scope: scope});
    }));

    it('Should create user', function () {
        expect(scope.usersignup).toEqual(false);
        expect(scope.usersignupmsg).toEqual("");
        scope.username = "user";
        scope.password = "test";
        httpBackendMock.expectPUT('api/make').respond(response);
        scope.signup();
        httpBackendMock.flush();
        expect(scope.usersignup).toEqual(true);
        expect(scope.usersignupmsg).toEqual("You have succesfully signed up");
    });
});

