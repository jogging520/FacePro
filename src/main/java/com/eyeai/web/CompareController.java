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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eyeai.service.FaceCompareService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
public class CompareController {
	
	@Autowired
	 private FaceCompareService faceCompareService;
	
	 private Logger logger=Logger.getLogger(getClass());
	 
    @ApiOperation(value="比对人证照片", notes="上传人证比对照片（单张照片），通过页面返回比对结果")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "HttpServletRequest", value = "http请求", required = true, dataType = "HttpServletRequest"),
        @ApiImplicitParam(name = "MultipartHttpServletRequest", value = "带附件的request请求", required = true, dataType = "MultipartHttpServletRequest")
    })
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
    
    @ApiOperation(value="比对人证照片", notes="上传人证比对照片（单张照片），返回json结果")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "HttpServletRequest", value = "http请求", required = true, dataType = "HttpServletRequest"),
        @ApiImplicitParam(name = "MultipartHttpServletRequest", value = "带附件的request请求", required = true, dataType = "MultipartHttpServletRequest")
    })
	@RequestMapping(value = "/CompareFacejson", method = RequestMethod.POST)
	@ResponseBody
     public String oneUploadFilejson(ModelMap map,HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {
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
		    
	    }
		return response;
	     
     }
	  @ApiOperation(value="比对人证照片（人、证）", notes="上传人证比对照片（两张照片（人、证）），通过页面返回比对结果")
	  @ApiImplicitParam(name = "HttpServletRequest", value = "http请求", required = true, dataType = "HttpServletRequest")
	  @RequestMapping(value = "/CompareutiFaces", method = RequestMethod.POST)
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
	  @ApiOperation(value="比对人证照片（人、证）", notes="上传人证比对照片（两张照片（人、证）），通过json返回比对结果")
	  @ApiImplicitParam(name = "HttpServletRequest", value = "http请求", required = true, dataType = "HttpServletRequest")
	  @RequestMapping(value = "/CompareutiFacesjson", method = RequestMethod.POST)
	  @ResponseBody
	 public String mutUploadFilesjson(ModelMap map,HttpServletRequest req) throws IOException {
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
			    
		    }
			return response;
	  }
	 
}
