package com.kep.kakaosync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.kep.kakaosync.kauth.KauthApiUtil;
import com.kep.kakaosync.model.User;
import com.kep.kakaosync.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
public class KakaoSyncService {
	@Autowired 
	KauthApiUtil kauthApiUtil;
	
	@Autowired
	UserRepository userRepository;
	
	public String saveUser(String code, String payerNumber) {
		JsonObject res = new JsonObject();
		
		try {
			JsonObject oauthUser = kauthApiUtil.oauthUser(code);
			
			String accessToken = oauthUser.get("access_token").toString();
			String refreshToken = oauthUser.get("refresh_token").toString();
			
			// 토큰에 대해 유저 아이디 등을 얻어온다
			JsonObject tokenInfo = kauthApiUtil.getTokenInfo(accessToken);
			Long userId = Long.parseLong(tokenInfo.get("id").toString());
			
			User user = new User(userId, payerNumber, accessToken, refreshToken);
			
			// db에 사용자 정보 저장
			userRepository.save(user);
			
			
			res.addProperty("accessToken", accessToken);
			res.addProperty("result", true);
		} catch (Exception e ) {
			e.printStackTrace();
			res.addProperty("result", false);
			res.addProperty("msg", "Save User Failed");
		}

		return res.toString();
	}

}
