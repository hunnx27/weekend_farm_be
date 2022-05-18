package com.demo.config.security.oauth2;

import com.demo.config.security.exception.CustomAuthenticationException;
import com.demo.modules.account.domain.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.naming.AuthenticationException;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String id, String name, Map<String, Object> attributes){
        if(RestrictionProvider.of(id) == null) throw new CustomAuthenticationException(id + " 는 호환되지 않는 소셜 로그인입니다.");

        if(id.equalsIgnoreCase(RestrictionProvider.KAKAO.name())){
            return ofKakao(name, attributes);
        }

        return null;
    }

    private static OAuthAttributes ofKakao(String name, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return new OAuthAttributes(attributes,
                name,
                (String) profile.get("nickname"),
                (String) kakaoAccount.get("email"),
                (String) profile.get("profile_image_url"));
    }
    public Account toEntity(){
        return new Account(name, email, picture);
    }
}
