package com.eyeai.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.eyeai.service.FileuploadService;
import com.eyeai.service.PostService;

@Controller
public class FileController {
	
	@Autowired
	 private FileuploadService fileservice;
	@Autowired
	 private PostService postService;
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
	  
	  public static byte[] getBytesFromFile(File f) {
	        if (f == null) {
	            return null;
	        }
	        try {
	            FileInputStream stream = new FileInputStream(f);
	            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
	            byte[] b = new byte[1000];
	            int n;
	            while ((n = stream.read(b)) != -1)
	                out.write(b, 0, n);
	            stream.close();
	            out.close();
	            return out.toByteArray();
	        } catch (IOException e) {
	        }
	        return null;
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
