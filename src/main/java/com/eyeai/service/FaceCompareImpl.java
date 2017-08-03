package com.eyeai.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
    
    @Value("${com.eyeai.api_key}")
    private String api_key;
    @Value("${com.eyeai.api_secret}")
    private String api_secret;
    @Value("${com.eyeai.compareurl}")
    private String url;
    
    private Logger logger = Logger.getLogger(getClass());
   
	@Override
	public String facecompare(HttpServletRequest req, MultipartHttpServletRequest multiReq) {
		//返回存储文件路径
		String imageStore="";
		//返回tokenlist
		List<String> tokenlist = new ArrayList<String>();
		//1.上传一张人证图片，存储到服务器中，返回存储地址
		try {
		    imageStore = fileService.upload(req, multiReq);
            logger.info("上传照片的存储路径为："+imageStore);

		    if(imageStore==null||imageStore.equals("")){
		    	logger.error("上传文件失败！");
		    	return "UPLOAD_FAILED";
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //2. 获取tokens（人、证件）
		 tokenlist = getTokenService.getFaceTokenList(imageStore);
         logger.info("获取image头像列表"+tokenlist.toString());
         if(tokenlist==null||tokenlist.size()==0||tokenlist.size()!=2)  {
            logger.error("获取头像列表非法！"+tokenlist.toString());	
        	return "IMAGE_NOTFIT";
         }

	    //3.进行比对

		return  CompareFaces(tokenlist);
	}


	private String CompareFaces(List<String> tokenlist) {
		String response="";
		// 拼装请求jsson
		JSONObject json = new JSONObject();
        json.put("api_key", api_key);
        json.put("api_secret", api_secret);
    	json.put("face_token1", tokenlist.get(0)) ;
    	json.put("face_token2", tokenlist.get(1));
        logger.info("发起比对请求:"+json.toJSONString());
        try {
        	response = postService.postJson(json, url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("比对网络返回失败！");
			e.printStackTrace();
		}
		return response;
	}

   //多文件比对
	@Override
	public String facecompareMuti(HttpServletRequest req) {
		//存储文件列表
	    List<String> list =new ArrayList<>();
	    //存储头像列表
	    List<String> tokenlist = new ArrayList<String>();
	    
	    //1.上传两张张图片（分别是人，证），存储到服务器中，返回存储地址list
		try {
			list = fileService.mutiupload(req);
			logger.info("上传文件后文件名称列表"+list.toString());
			if(list==null||list.size()==0){
				logger.error("多文件上传，获取文件名称列表失败！");
		    	return "UPLOAD_FAILED";
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("多文件上传，上传文件后文件名称列表！");
			e.printStackTrace();
		}
		//2. 获取tokens（人、证件）
		 for(String imageStore:list){
			 List<String> Temptokenlist = new ArrayList<String>();
			 Temptokenlist=getTokenService.getFaceTokenList(imageStore);

             if(Temptokenlist==null||Temptokenlist.size()==0||Temptokenlist.size()!=1)  return "IMAGE_NOTFIT";
             tokenlist.add(Temptokenlist.get(0));
		 }
		 logger.info("获取多文件头像比对列表："+tokenlist.toString());
	     return  CompareFaces(tokenlist);
	}

}
