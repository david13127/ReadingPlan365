var app = angular.module('myApp',
    [
        'ui.router',
        'mainModule',
        'secondModule',
        'firstModule'
    ]);

app.constant('BaseURL', "");
app.constant('SocketURL', "localhost:8080");
app.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('login');
    $stateProvider
        .state('login', {
            url: '/login',
            templateUrl: '/html/views/common/login.html',
            controller: 'mainController',
        })
        .state('signup', {
            url: '/signup',
            templateUrl: '/html/views/common/signup.html',
            controller: 'mainController',
        })
        .state('first', {
            url: '/first',
            templateUrl: '/html/views/first/first.html',
            controller: 'firstController'
        })
        .state('second', {
            url: '/second',
            templateUrl: '/html/views/second/second.html',
            controller: 'secondController'
        });
});
