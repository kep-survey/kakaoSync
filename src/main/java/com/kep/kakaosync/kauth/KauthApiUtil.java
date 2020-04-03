package com.kep.kakaosync.kauth;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Component
public class KauthApiUtil {
	private static final String AUTH_GRANT_TYPE = "authorization_code";
	private static final String REFRESH_GRANT_TYPE = "refresh_token";
	private static final String CLIENT_ID = "d0fc0dee9fc59f4c32df40333a67aa19";
	private static final String REDIRECT_URI = "http://localhost:8080/additionalInfo";
	
	private static final String OAUTH_TOKEN_API = "https://kauth.kakao.com/oauth/token";
	private static final String USER_ME_API = "https://kapi.kakao.com/v2/user/me";
	private static final String ACCESS_TOKEN_INFO_API = "https://kapi.kakao.com/v1/user/access_token_info";
    
    public JsonObject oauthUser(String code) {
    	// 헤더 선언
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		// 파라미터 담기
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", AUTH_GRANT_TYPE);
		params.add("client_id", CLIENT_ID);
		params.add("redirect_uri", REDIRECT_URI);
		params.add("code", code);
		
		// http entity 선언
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		// 요청 보내기
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    	String resp = restTemplate.exchange(OAUTH_TOKEN_API, HttpMethod.POST, httpEntity, String.class).getBody();
    	
    	// string json으로 파싱
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(resp);
        
        return element.getAsJsonObject();
    }
    
    public JsonObject getUser(String accessToken) {
		// 헤더 설정
    	HttpHeaders headers = new HttpHeaders();
        headers.add("Authentication", String.format("Bearer %s", accessToken));
        
        // http entity 선언
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
        
        // 요청보내기
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        String resp = restTemplate.exchange(USER_ME_API, HttpMethod.GET, httpEntity, String.class).getBody();
        
        // string json으로 파싱
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(resp);
        
        return element.getAsJsonObject();
	}
    
    public JsonObject getTokenInfo(String accessToken){
        // access token을 헤더에 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", accessToken));
        
        // http entity 선언
        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
        
        // 요청보내기
        RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        String resp = restTemplate.exchange(ACCESS_TOKEN_INFO_API, HttpMethod.GET, httpEntity, String.class).getBody();
        
        // string json으로 파싱
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(resp);
        
        return element.getAsJsonObject();
    }
    
    public JsonObject refreshToken(String refreshToken){
    	// 헤더 설정
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		// 파라미터 담
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", REFRESH_GRANT_TYPE);
		params.add("client_id", CLIENT_ID);
		params.add("refresh_token", refreshToken);
		
		// http entity 선언
		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		// 요청보내기
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		String resp = restTemplate.exchange(OAUTH_TOKEN_API, HttpMethod.POST, httpEntity, String.class).getBody();
    	
    	// string json으로 파싱
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(resp);
        
        return element.getAsJsonObject();
    }
}
