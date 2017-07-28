package com.eyeai.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eyeai.service.FaceCompareService;

@Controller
public class CompareController {
	
	@Autowired
	 private FaceCompareService faceCompareService;
	
	@RequestMapping(value = "/CompareFace", method = RequestMethod.POST)
     public String oneUploadFile(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {
        
		faceCompareService.facecompare(req, multiReq);
	    return "sucess";
     }

	  @RequestMapping(value = "/mutiUploadFiles", method = RequestMethod.POST)
	  public String mutUploadFiles(HttpServletRequest req) throws IOException {
	    
		//fileservice.mutiupload(req);
		  
	    return "sucess";
	  }
	  
	 
}
