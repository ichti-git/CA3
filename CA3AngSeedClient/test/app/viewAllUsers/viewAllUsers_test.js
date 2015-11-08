describe('myApp.viewAllUsers viewAllUsersCtrl', function () {

    var scope, httpBackendMock, ctrl;
    var allUsers = [{userName:"admin",password:"test",roles:[{role:"Admin"},{role:"User"}]},
                    {userName:"deletee",password:"test",roles:[{role:"User"}]},
                    {userName:"user",password:"test",roles:[{role:"User"}]}];
    var deleteUser = {userName:"deletee",password:"test",roles:[{role:"User"}]};

    beforeEach(module('myApp.viewAllUsers'));

    beforeEach(inject(function ($httpBackend, $rootScope, $controller) {
        httpBackendMock = $httpBackend;
        httpBackendMock.expectGET('api/admin/users').respond(allUsers);
        scope = $rootScope.$new();
        ctrl = $controller('ViewAllUsers', {$scope: scope});
    }));

    it('Should fetch users', function () {
        expect(scope.users).toEqual([]);
        httpBackendMock.flush();
        expect(scope.users[0].userName).toEqual("admin");
        expect(scope.users[0].roles[0].role).toEqual("Admin");
        expect(scope.users[0].roles[1].role).toEqual("User");
        expect(scope.users[2].userName).toEqual("user");
        expect(scope.users[2].roles[0].role).toEqual("User");
        expect(scope.users[2].roles[1]).toBeUndefined();
    });

    it('Should delete user', function () {
        expect(scope.userdel).toEqual(null);
        httpBackendMock.flush();
        expect(scope.users[1].userName).toEqual("deletee");
        expect(scope.users[2].userName).toEqual("user");
        httpBackendMock.expectDELETE('api/admin/user/deletee').respond(deleteUser);
        scope.deleteUser('deletee');
        httpBackendMock.flush();
        expect(scope.userdel).toEqual("deletee");
        expect(scope.users[1].userName).toEqual("user");
        expect(scope.users[2]).toBeUndefined();
    });
});

