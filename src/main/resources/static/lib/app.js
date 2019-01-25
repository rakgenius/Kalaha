var app = angular.module('kalahaGame', [])
app.controller('gameController', function($scope, $http) {
    console.log("game id is " + $scope.gameId);
    $http.post("/api/newgame/").then(function(resp){
        $scope.game = resp.data;
    })

    $scope.play = function(gameId, playerId, pitId){

        $scope.errorMessage = "";
        $http.put("/api/games/"+gameId+"/pits/"+pitId+"").then(function(resp){
            console.log(resp.data);
            $scope.game = resp.data;
            $scope.pitMap = resp.data.board.pitMap;

            $scope.total = 0
            angular.forEach(resp.data.board.pitMap, function(pit, key) {
                $scope.total += pit.stoneCount;
            });

        }).catch(function(resp){
            console.log(resp);
            $scope.errorMessage = resp.data.message;
        })
    }
});
