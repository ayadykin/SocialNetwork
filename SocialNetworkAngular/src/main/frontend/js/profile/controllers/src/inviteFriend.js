$scope.inviteFriend = function(userId) {

    $log.info('inviteFriend userId : ' + userId);

    FriendRest.inviteFriend({
	userId : userId
    }, function(data) {
	$scope.successMessageHandler("Friend invite success");
    });
};