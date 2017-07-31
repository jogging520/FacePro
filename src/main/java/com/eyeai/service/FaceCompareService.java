package com.eyeai.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FaceCompareService {
	
 
	  String facecompare(HttpServletRequest req, MultipartHttpServletRequest multiReq);
	  String facecompareMuti(HttpServletRequest req) ;
}
