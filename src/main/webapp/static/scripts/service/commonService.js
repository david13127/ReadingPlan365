var app = angular.module('mainModule', []);
app.service('RestBase', function($rootScope, $http) {
    this.get = function (config, successCallback, errorCallback) {
        $http({
            method:'GET',   //提交方式
            url:config.url,//提交路径
            params:config.params   //传递到后台的数据，json对象
        }).then(successCallback,errorCallback);
    };
    this.post = function (config, successCallback, errorCallback) {
        $http({
            method:'POST',   //提交方式
            url:config.url,//提交路径
            params:config.params,   //传递到后台的数据，json对象
            data:config.data
        }).then(successCallback,errorCallback);
    };
});