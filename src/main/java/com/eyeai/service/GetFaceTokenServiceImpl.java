package com.eyeai.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
@Service
public class GetFaceTokenServiceImpl implements GetFaceTokenService {
	@Autowired
	private PostService postService;
	List<String> tokenlist = new ArrayList<String>();
	
	String urldetect = "https://api-cn.faceplusplus.com/facepp/v3/detect";
	String response ="";
	@Override
	public List<String> getFaceTokenList(String imageStore) {
		//1.上传比对文件，获取结果
		File file = new File(imageStore); 
        byte[] buff = postService.getBytesFromFile(file);
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", "EmwiWkkws71IqE1zQbdjkATEHgGlBeSq");
        map.put("api_secret", "p0kdAQ8heNM5_sM7H0_0Gex4ZV6exHO3");
        byteMap.put("image_file", buff);
        try {
			response =postService.postImage(urldetect, map, byteMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       //2. 获取token list
 	    JSONObject faceDetecReturn=JSON.parseObject(response);
 	    String faces=faceDetecReturn.getString("faces");
        List<HashMap> list =JSON.parseArray(faces, HashMap.class); 
        List<String> tokenlist =new ArrayList<String>();
        if(list!=null||list.size()>1){
	        for(int i=0;i<list.size();i++){
	        	tokenlist.add((String)list.get(i).get("face_token"));
	        }
        }
        System.out.println("tokenlist is "+tokenlist);
		return tokenlist;
	}

	@Override
	public String getFaceToken(String imageStore) {
		// TODO Auto-generated method stub
		return null;
	}

}
