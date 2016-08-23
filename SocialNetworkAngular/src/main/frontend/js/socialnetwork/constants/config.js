angular.module('socialNetworkApp').constant('config', {
    basePath: "/SocialNetworkApi/",
    
    friendPath: "/SocialNetworkApi/friend",
    
    groupPath: "/SocialNetworkApi/group",
    friendsNotInGroup: "/SocialNetworkApi/group/friends_not_in_group",
    deleteUserFromGroupPath: "/SocialNetworkApi/group/delete_user",
    addUserToGroupPath: "/SocialNetworkApi/group/add_user",
    leaveGroupPath: "/SocialNetworkApi/group/leave_group",
    
    signinPath: "/SocialNetworkApi/j_spring_security_check",
    signinInit: "/SocialNetworkApi/signin",
    
    profilePath: "/SocialNetworkApi/profile",
    
    i18nCacheKey: "i18nCache"
});