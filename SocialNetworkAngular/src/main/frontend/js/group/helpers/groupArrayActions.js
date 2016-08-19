function findGroupById(groups, groupId) {
    var result = $.grep(groups, function(e) {
	return e.groupId == groupId;
    });
    if (result.length === 0) {
	alert("group not found");
    } else if (result.length == 1) {
	return result[0];
    } else {
	alert("multiple items found");
    }
    return 0;
}

function removeGroupById(groups, groupId) {
    for (var i = 0; i < groups.length; i++)
	if (groups[i].groupId == groupId) {
	    groups.splice(i, 1);
	    break;
	}
}