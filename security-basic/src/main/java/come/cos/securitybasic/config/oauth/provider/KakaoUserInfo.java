package come.cos.securitybasic.config.oauth.provider;
import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return  String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return (String) ((Map) attributes.get("properties")).get("properties");
    }

    @Override
    public String getEmail() {
        return (String) ((Map) attributes.get("kakao_account")).get("email");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

}