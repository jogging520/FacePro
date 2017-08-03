package com.eyeai.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
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
	
	private Logger logger=Logger.getLogger(getClass());
	
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
			logger.info("请求头像获取接口，获得头像返回："+response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       //2. 获取token list    
      //头像列表
    	List<String> tokenlist = new ArrayList<String>();
 	    JSONObject faceDetecReturn=JSON.parseObject(response);
 	    //长传图片后，返回空值，或者错误
 	    if(faceDetecReturn==null||faceDetecReturn.containsKey("error_message")){
 	    	logger.error("头像detect接口返回错误："+faceDetecReturn.toJSONString());
 	    	return tokenlist;
 	    }
 	    String faces=faceDetecReturn.getString("faces");
        List<HashMap> list =JSON.parseArray(faces, HashMap.class); 
        
        if(null!=list){
	        for(int i=0;i<list.size();i++){
	        	tokenlist.add((String)list.get(i).get("face_token"));
	        }
        }
        logger.info("获得头像列表"+tokenlist);
		return tokenlist;
	}

	@Override
	public String getFaceToken(String imageStore) {
		// TODO Auto-generated method stub
		return null;
	}

}
