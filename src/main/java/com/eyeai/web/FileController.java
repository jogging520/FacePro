package com.eyeai.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eyeai.service.FileuploadService;

@Controller
public class FileController {
	
	@Autowired
	 private FileuploadService fileservice;
	
	@RequestMapping(value = "/oneUploadFile", method = RequestMethod.POST)
     public String oneUploadFile(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {

	      
	    fileservice.upload(req,multiReq);

	    return "sucess";
     }

	  @RequestMapping(value = "/mutiUploadFiles", method = RequestMethod.POST)
	  public String mutUploadFiles(HttpServletRequest req) throws IOException {
	    
		fileservice.mutiupload(req);
		  
	    return "sucess";
	  }
//	@RequestMapping(value = "/testDownload", method = RequestMethod.GET)
//	  public void testDownload(HttpServletResponse res) {
//	    String fileName = "upload.jpg";
//	    res.setHeader("content-type", "application/octet-stream");
//	    res.setContentType("application/octet-stream");
//	    res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//	    byte[] buff = new byte[1024];
//	    BufferedInputStream bis = null;
//	    OutputStream os = null;
//	    try {
//	      os = res.getOutputStream();
//	      bis = new BufferedInputStream(new FileInputStream(new File("d://"
//	          + fileName)));
//	      int i = bis.read(buff);
//	      while (i != -1) {
//	        os.write(buff, 0, buff.length);
//	        os.flush();
//	        i = bis.read(buff);
//	      }
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    } finally {
//	      if (bis != null) {
//	        try {
//	          bis.close();
//	        } catch (IOException e) {
//	          e.printStackTrace();
//	        }
//	      }
//	    }
//	    System.out.println("success");
//	  }
//	
//	  public String getFilename(String uploadFilePath){
//
//	  System.out.println("uploadFlePath:" + uploadFilePath);
//	    
//	    // 1.截取上传文件的文件名
//	    String uploadFileName = uploadFilePath.substring(
//	        uploadFilePath.lastIndexOf('/') + 1, uploadFilePath.indexOf('.'));
//	    System.out.println("multiReq.getFile()" + uploadFileName);
//	    // 2.截取上传文件的后缀
//
//	    return uploadFileName;    
//	}

}
