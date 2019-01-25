var app = angular.module('loadGame', [])

app.controller('loadGameController', function($scope, $http) {

    $scope.getGame = function() {
        console.log("game id is " + $scope.gameId);
        var url = "/mongo/games/load/" + $scope.gameId;
        console.log("url is " + url);
        var config = {
            headers: {'Content-Type': 'application/json;charset=utf-8;'},

            params: {'gameId': $scope.gameId}
        }

        console.log("url is " + url);
        $http.get(url).then(function (response) {
            console.log("url is " + url);
            $scope.game = response.data;
            $scope.errorMessage = "";
        }).catch(function (response) {
            $scope.game = "";
            $scope.errorMessage = response.data.message;
        })
    }
});
