package com.example.team_project.security.oauth2.userinfo;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getNickname() {
        return (String) attributes.get("name");
    }


}
