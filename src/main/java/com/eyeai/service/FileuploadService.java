package com.eyeai.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileuploadService {
	
	 public void upload(HttpServletRequest req, MultipartHttpServletRequest multiReq ) throws IOException;
	 
	 public void mutiupload(HttpServletRequest req) throws IOException;

}
