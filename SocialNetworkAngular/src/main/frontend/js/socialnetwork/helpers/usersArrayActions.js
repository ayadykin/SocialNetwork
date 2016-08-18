function findUserById(users, userId) {
    var result = $.grep(users, function(e) {
	return e.userId == userId;
    });
    if (result.length == 0) {
	alert("User not found");
    } else if (result.length == 1) {
	return result[0];
    } else {
	alert("multiple items found");
    }
    return 0;
}

function removeUserById(users, userId) {
    for (var i = 0; i < users.length; i++)
	if (users[i].userId == userId) {
	    users.splice(i, 1);
	    break;
	}
}