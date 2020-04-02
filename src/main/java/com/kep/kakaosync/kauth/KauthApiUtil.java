package com.kep.kakaosync.kauth;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class KauthApiUtil {
	private static final String GRANT_TYPE = "authorization_code";
	private static final String CLIENT_ID = "95a29b52a930a2a11df6c8090bd8df85";
	private static final String REDIRECT_URI = "";
	
	private static final String OAUTH_TOKEN_API = "https://kauth.kakao.com/oauth/token";
	private static final String USER_ME_API = "https://kapi.kakao.com/v2/user/me";
    
    public JsonObject oauthUser(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", GRANT_TYPE);
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", REDIRECT_URI);
		params.add("code", code);
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    	String resp =  restTemplate.postForEntity(USER_ME_API, request, String.class).getBody();
    	
    	// string json으로 파싱
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(resp);
        
        return element.getAsJsonObject();
    }
    
    public JsonObject getUser(String accessToken) {
		// 헤더 설정
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication", "Bearer " + accessToken);
        
        // 엔티티에 헤더 담기
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
        
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        String resp = restTemplate.exchange(USER_ME_API, HttpMethod.GET, httpEntity, String.class).getBody();
        
        // string json으로 파싱
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(resp);
        
        return element.getAsJsonObject();
	}
}
