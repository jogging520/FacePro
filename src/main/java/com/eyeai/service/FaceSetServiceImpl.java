package com.eyeai.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

public class FaceSetServiceImpl implements FaceSetService {
    @Autowired
	private PostService postservice;
	
	@Override
	public String createFaceSet() throws IOException {
        JSONObject json = new JSONObject();
        json.put("api_key", "EmwiWkkws71IqE1zQbdjkATEHgGlBeSq");
        json.put("api_secret", "p0kdAQ8heNM5_sM7H0_0Gex4ZV6exHO3");
        String url ="https://api-cn.faceplusplus.com/facepp/v3/faceset/create";
       
		return postservice.postJson(json, url);
	}

	@Override
	public void addFaceSet(String faceset_token, String face_token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeFaceSet(String faceset_token, String face_token) {
		// TODO Auto-generated method stub
		
	}
	

}
