package com.eyeai.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
@Service
public class FaceCompareImpl implements FaceCompareService {
	@Autowired
	private  FileuploadService fileService;
    @Autowired
	private  PostService postService;
    @Autowired
    private GetFaceTokenService getTokenService;
   

	@Override
	public String facecompare(HttpServletRequest req, MultipartHttpServletRequest multiReq) {
		String imageStore="";
		List<String> tokenlist = new ArrayList<String>();
		//1.上传一张人证图片，存储到服务器中，返回存储地址
		try {
		    imageStore = fileService.upload(req, multiReq);
		    System.out.println("imageStore "+imageStore );
		    if(imageStore==null||imageStore.equals("")){
		    	return "UPLOAD_FAILED";
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //2. 获取tokens（人、证件）
		 tokenlist = getTokenService.getFaceTokenList(imageStore);
System.out.println("tokenlist "+tokenlist );
        if(tokenlist==null||tokenlist.size()==0||tokenlist.size()!=2)  return "IMAGE_NOTFIT";

	    //3.进行比对

		return  CompareFaces(tokenlist);
	}


	private String CompareFaces(List<String> tokenlist) {
		String response="";
		JSONObject json = new JSONObject();
    	String url ="https://api-cn.faceplusplus.com/facepp/v3/compare";
        json.put("api_key", "EmwiWkkws71IqE1zQbdjkATEHgGlBeSq");
        json.put("api_secret", "p0kdAQ8heNM5_sM7H0_0Gex4ZV6exHO3");
    	json.put("face_token1", tokenlist.get(0)) ;
    	json.put("face_token2", tokenlist.get(1));

  System.out.println(json.toJSONString());
        try {
        	response = postService.postJson(json, url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}


	@Override
	public String facecompareMuti(HttpServletRequest req) {
		
	    List<String> list =new ArrayList<>();
	    List<String> tokenlist = new ArrayList<String>();
	    
	  //1.上传两张张图片（分别是人，证），存储到服务器中，返回存储地址list
		try {
			list = fileService.mutiupload(req);
			if(list==null||list.size()==0){
		    	return "UPLOAD_FAILED";
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//2. 获取tokens（人、证件）
		 for(String imageStore:list){
			 List<String> Temptokenlist = new ArrayList<String>();
			 Temptokenlist=getTokenService.getFaceTokenList(imageStore);
//System.out.println("tokenlist "+tokenlist );
             if(tokenlist==null||tokenlist.size()==0||tokenlist.size()!=1)  return "IMAGE_NOTFIT";
             tokenlist.add(Temptokenlist.get(0));
		 }
		 
	     return  CompareFaces(tokenlist);
	}

}
