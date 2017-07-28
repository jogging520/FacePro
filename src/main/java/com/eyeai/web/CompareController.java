package com.eyeai.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eyeai.service.FaceCompareService;

@Controller
public class CompareController {
	
	@Autowired
	 private FaceCompareService faceCompareService;
	
	@RequestMapping(value = "/CompareFace", method = RequestMethod.POST)
	@ResponseBody
     public String oneUploadFile(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {
        String response="";
		response=faceCompareService.facecompare(req, multiReq);
		if(response.equals("")){
			return "比对失败！";
		}
		if(response.equals("UPLOAD_FAILED")){
			return "文件上传失败！";
		}
		if(response.equals("IMAGE_NOTFIT")){
			return "人证比对照片非法！";
		}
		JSONObject json=JSON.parseObject(response);
	    return json.get("confidence").toString();
     }

	  @RequestMapping(value = "/mutiUploadFiles", method = RequestMethod.POST)
	  public String mutUploadFiles(HttpServletRequest req) throws IOException {
	    
		//fileservice.mutiupload(req);
		  
	    return "sucess";
	  }
	  
	 
}
