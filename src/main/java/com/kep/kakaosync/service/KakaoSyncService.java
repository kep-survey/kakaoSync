package com.kep.kakaosync.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.kep.kakaosync.kauth.KauthApiUtil;

@Service
public class KakaoSyncService {
	
	@Autowired KauthApiUtil kauthApiUtil;

	public String saveUser(String code, String payerNumber) {
		JsonObject res = new JsonObject();
		
		try {
			JsonObject oauthUser = kauthApiUtil.oauthUser(code);
			
			String accessToken = oauthUser.get("access_token").toString();
			
			System.out.println(kauthApiUtil.getUser(accessToken).toString());
			
			res.addProperty("result", true);
		} catch (Exception e ) {
			e.printStackTrace();
			res.addProperty("result", false);
			res.addProperty("msg", "Save User Failed");
		}

		return res.toString();
	}

}
