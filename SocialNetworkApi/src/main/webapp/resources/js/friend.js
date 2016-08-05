function registerAcceptInvitation(userId) {
	$("#acceptInvitation_" + userId).click(function() {
		acceptInvitation(userId);
	});
}

function registerDeclineInvitation(userId) {
	$("#declineInvitation_" + userId).click(function() {
		declineInvitation(userId);
	});
}

function inviteFriend(userId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/friend/inviteFriend",
		data : {
			_csrf : token,
			userId : userId
		},
		success : function(result) {
			$("#friendStatus_" + userId).html("Wait for accept invite");
		}
	});
}

function declineInvitation(userId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/friend/declineInvitation",
		data : {
			_csrf : token,
			userId : userId
		},
		success : function(result) {
			$("#invitation_message_" + userId).hide();
		}
	});
}

function acceptInvitation(userId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/friend/acceptInvitation",
		data : {
			_csrf : token,
			userId : userId
		},
		success : function(result) {
			$("#invitation_message_" + userId).hide();
		}
	});
}

function deleteFriend(token, friendId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/friend/deleteFriend",
		data : {
			_csrf : token,
			friendId : friendId
		},
		success : function(result) {
			$("#friend_" + friendId).hide();
		}
	});
}