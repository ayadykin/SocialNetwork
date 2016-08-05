function showCreateGroup() {
	$("#createGroup").show();
	$("#groupList").hide();
}
function showGroupList() {
	$("#createGroup").hide();
	$("#groupList").show();
}

function addUserToGroup(groupId, userId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/group/adduser",
		data : {
			_csrf : token,
			groupId : groupId,
			userId : userId
		},
		success : function(result) {
			$("#groupFriend_" + userId).html('');
		}
	});
}

function deleteUserFromGroup(groupId, userId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/group/deleteuser",
		data : {
			_csrf : token,
			groupId : groupId,
			userId : userId
		},
		success : function(result) {
			$("#groupUser_" + userId).html('');
		}
	});
}

function leaveGroup(groupId, userId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/group/leavegroup",
		data : {
			_csrf : token,
			groupId : groupId,
			userId : userId
		},
		success : function(result) {
			$("#groupUser_" + userId).html('');
		}
	});
}

function deleteGroup(groupId) {
	$.ajax({
		type : "POST",
		url : contextPath + "/group/deletegroup",
		data : {
			_csrf : token,
			groupId : groupId
		},
		success : function(result) {
			$("#group_" + groupId + " #group_hidden").html("true");
		}
	});
}