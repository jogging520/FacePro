package com.eyeai.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
@Service
public class GetFaceTokenServiceImpl implements GetFaceTokenService {
	@Autowired
	private PostService postService;
	@Value("${com.eyeai.api_key}")
	private String api_key;
	@Value("${com.eyeai.api_secret}")
	private String api_secret;
	@Value("${com.eyeai.detecturl}")
	private String url;
	
	List<String> tokenlist = new ArrayList<String>();
	
	//String urldetect = "https://api-cn.faceplusplus.com/facepp/v3/detect";
	String response ="";
	@Override
	public List<String> getFaceTokenList(String imageStore) {
		//1.上传比对文件，获取结果
		File file = new File(imageStore); 
        byte[] buff = postService.getBytesFromFile(file);
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", api_key);
        map.put("api_secret", api_secret);
        byteMap.put("image_file", buff);
        try {
			response =postService.postImage(url, map, byteMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       //2. 获取token list
 	    JSONObject faceDetecReturn=JSON.parseObject(response);
 	    String faces=faceDetecReturn.getString("faces");
        List<HashMap> list =JSON.parseArray(faces, HashMap.class); 
        List<String> tokenlist =new ArrayList<String>();
        if(null!=list||list.size()>1){
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
