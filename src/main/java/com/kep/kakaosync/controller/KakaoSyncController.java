package com.kep.kakaosync.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.kep.kakaosync.service.KakaoSyncService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/*")
public class KakaoSyncController {
	@Autowired
	KakaoSyncService kakaoSyncService;
	
	@PostMapping("/saveUser")
	public String saveUser(@RequestBody String req) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode body = objectMapper.readValue(req, JsonNode.class);
			
			String code = body.get("code").asText();
			String payerNumber = body.get("payerNumber").asText();
			
			return kakaoSyncService.saveUser(code, payerNumber);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			
			JsonObject res = new JsonObject();
			res.addProperty("result", "false");
			res.addProperty("msg", "Json processing error occurred");
			
			return res.toString();
		}
	}
}
