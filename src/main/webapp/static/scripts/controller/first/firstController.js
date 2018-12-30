/**
 * Created by huangyao on 2017/7/14.
 */
angular.module('firstModule', ['ngWebSocket'])
    // .factory('socketData', function ($websocket, $location, SocketURL) {
    //     var host = SocketURL;
    //     // if ($location.port() && $location.port() !== 80 && $location.port() !== 443) {
    //     //     host = host + ':' + $location.port();
    //     // }
    //     var ws = "ws";
    //     if ($location.protocol() === 'https') {
    //         ws = "wss";
    //     }
    //     // 开始连接
    //     var dataStream = $websocket(ws + '://' + host + '/websocket');
    //     dataStream.reconnectIfNotNormalClose = true;
    //     var collection = [];
    //     var methods = {
    //         lastestdata: {},
    //         readyState: 0,
    //         collection: collection,
    //         sendData: function (data) {
    //             dataStream.send(JSON.stringify(data));
    //         }
    //     };
    //     dataStream.onMessage(function (message) {
    //         methods.readyState = dataStream.readyState;
    //         methods.lastestdata = JSON.parse(message.data);
    //         collection.push(JSON.parse(message.data));
    //
    //         //这里可以直接跳转到制定的页面，我这里是跳转到了我的地图页面
    //         //window.location = '/#/app/Map/Index'
    //     });
    //
    //     dataStream.onError(function (message) {
    //
    //         //监控状态变化，实时跟进连接状态
    //         methods.readyState = dataStream.readyState;
    //     });
    //
    //     dataStream.onOpen(function (message) {
    //         methods.readyState = dataStream.readyState;
    //     });
    //
    //     dataStream.onClose(function (message) {
    //         methods.readyState = dataStream.readyState;
    //     });
    //
    //     return methods;
    // })
    .controller('firstController', function ($scope) {
    });