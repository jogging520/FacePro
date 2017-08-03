package com.eyeai.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eyeai.service.FaceCompareService;

@Controller
public class CompareController {
	
	@Autowired
	 private FaceCompareService faceCompareService;
	
	 private Logger logger=Logger.getLogger(getClass());
	
	@RequestMapping(value = "/CompareFace", method = RequestMethod.POST)
	//@ResponseBody
     public String oneUploadFile(ModelMap map,HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {
        String response="";
		response=faceCompareService.facecompare(req, multiReq);

		if(response.equals("UPLOAD_FAILED")){
			map.addAttribute("resposne", "文件上传失败");
			return "fail";
		}
		else if(response.equals("IMAGE_NOTFIT")){
			map.addAttribute("resposne", "人证比对照片非法！");
			return "fail";
		}else{
			if(response.equals("")){
				map.addAttribute("resposne", "比对失败,请重新比对！");
				return "fail";
			}
			
			JSONObject json=JSON.parseObject(response);
			
		    if(null!=json.get("error_message")){
				map.addAttribute("resposne", "比对失败,请重新比对！");
				return "fail";
			}
		    String score = json.get("confidence").toString();
	
            float s = Float.parseFloat((score.trim()));
            logger.info("单张头像获取比对分数:"+s);
		    if(s >= 60.0){
		        map.addAttribute("response", "比对成功!人证一致!");
		    }else{
			   map.addAttribute("response", "比对完毕！人证不一致！");
		    }
		    map.addAttribute("score",score);
	    }
		return "sucess";
	     
     }

	  @RequestMapping(value = "/mutiUploadFiles", method = RequestMethod.POST)
	  public String mutUploadFiles(ModelMap map,HttpServletRequest req) throws IOException {
		    String response="";
			response=faceCompareService.facecompareMuti(req);
			if(response.equals("UPLOAD_FAILED")){
				map.addAttribute("resposne", "文件上传失败");
				return "fail";
			}
			else if(response.equals("IMAGE_NOTFIT")){
				map.addAttribute("resposne", "人证比对照片非法！");
				return "fail";
			}else{
				if(response.equals("")){
					map.addAttribute("resposne", "比对失败,请重新比对！");
					return "fail";
					}
				
				JSONObject json=JSON.parseObject(response);
				
			    if(null!=json.get("error_message")){
					map.addAttribute("resposne", "比对失败,请重新比对！");
					return "fail";
				}
			    String score = json.get("confidence").toString();
		
	            float s = Float.parseFloat((score.trim()));
	            logger.info("多张头像照片获取比对分数:"+s);
			    if(s >= 60.0){
			        map.addAttribute("response", "比对成功!人证一致!");
			    }else{
				   map.addAttribute("response", "比对完毕！人证不一致！");
			    }
			    map.addAttribute("score",score);
		    }
			return "sucess";
	  }
	  
	 
}
