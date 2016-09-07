angular.module('socialNetworkApp').constant('config', {
    basePath: "/SocialNetworkApi/",
    
    friendPath: "/SocialNetworkApi/friend",
    
    groupPath: "/SocialNetworkApi/group",
    deleteUserFromGroupPath: "/SocialNetworkApi/group/delete_user",
    addUserToGroupPath: "/SocialNetworkApi/group/add_user",
    leaveGroupPath: "/SocialNetworkApi/group/leave_group",
    
    chatPath: "/SocialNetworkApi/chat",
    
    signinPath: "/SocialNetworkApi/signin",
    logoutPath: "/SocialNetworkApi/logout",
    
    profilePath: "/SocialNetworkApi/profile",
    
    i18nCacheKey: "i18nCache"
});