package com.demo.config.security.oauth2;

public enum RestrictionProvider {
    KAKAO, GOOGLE, FACEBOOK;

    public static RestrictionProvider of(String id){
        for (RestrictionProvider provider : RestrictionProvider.values()){
            if(provider.name().equalsIgnoreCase(id)) return provider;
        }
        return null;
    }

}
