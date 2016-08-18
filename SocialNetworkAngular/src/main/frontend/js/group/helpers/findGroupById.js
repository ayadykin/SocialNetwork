function findGroupById(groups, groupId) {
    var result = $.grep(groups, function(e) {
	return e.groupId == groupId;
    });
    if (result.length == 0) {
	alert("group not found");
    } else if (result.length == 1) {
	return result[0];
    } else {
	alert("multiple items found");
    }
    return 0;
}