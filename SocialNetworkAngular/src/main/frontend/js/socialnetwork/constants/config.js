angular.module('socialNetworkApp').constant('config', {
    basePath: "/SocialNetworkApi/",
    
    friendPath: "/SocialNetworkApi/friend",
    
    groupPath: "/SocialNetworkApi/group",
    deleteUserFromGroupPath: "/SocialNetworkApi/group/delete_user",
    addUserToGroupPath: "/SocialNetworkApi/group/add_user",
    leaveGroupPath: "/SocialNetworkApi/group/leave_group",
    
    chatPath: "/SocialNetworkApi/chat",
    
    signinPath: "/AuthService/oauth/token",
    tokenPath: "/AuthService/oauth/token",
    logoutPath: "/SocialNetworkApi/logout",
    
    profilePath: "/SocialNetworkApi/profile",
    
    i18nCacheKey: "i18nCache"
});