package com.eyeai.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class FileuploadService {
    FileOutputStream fos = null;
    public void upload(HttpServletRequest req, MultipartHttpServletRequest multiReq ) throws IOException{
    	
		// 获取上传文件的路径
	    String uploadFilePath = multiReq.getFile("file1").getOriginalFilename();
	    // 截取上传文件的文件名
	    String uploadFileName = getFilename(uploadFilePath);
	    // 截取上传文件的后缀
	    String uploadFileSuffix =getFileSuffix(uploadFilePath);
	    // 获取上传文件的输入流
	    FileInputStream fis = (FileInputStream)multiReq.getFile("file1").getInputStream();
		
	    transferFile(fis,uploadFileName,uploadFileSuffix);
    }
    
    public void mutiupload(HttpServletRequest req) throws IOException{
    	List<MultipartFile> files = ((MultipartHttpServletRequest) req).getFiles("file");
	    MultipartFile file = null;
	    // addtheimage judeg conunt
	    
	    //
	    for (int i = 0; i < files.size(); ++i) {
	        file = files.get(i);
			// 获取上传文件的路径
		    String uploadFilePath = file.getOriginalFilename();
		    // 截取上传文件的文件名
		    String uploadFileName = getFilename(uploadFilePath);
		    // 截取上传文件的后缀
		    String uploadFileSuffix =getFileSuffix(uploadFilePath);
		    // 获取上传文件的输入流
		    FileInputStream fis = (FileInputStream) file.getInputStream();
	        if (!file.isEmpty()) {
	    	    transferFile(fis,uploadFileName,uploadFileSuffix);
	      } else {
	        System.out.println("上传文件为空");
	      }
	    }
	    System.out.println("多文件上传成功了");
    }
    public String getFilename(String uploadFilePath){
  	    
    	
  	    // 1.截取上传文件的文件名
  	    String uploadFileName = uploadFilePath.substring(
  	        uploadFilePath.lastIndexOf('/') + 1, uploadFilePath.indexOf('.'));
  	    System.out.println("multiReq.getFile()" + uploadFileName);
  	    // 2.截取上传文件的后缀

  	    return uploadFileName;    
  	}
  	public String getFileSuffix(String uploadFilePath){
  	    String uploadFileSuffix = uploadFilePath.substring(
  		        uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
  		System.out.println("uploadFileSuffix:" + uploadFileSuffix);
  		
  		return uploadFileSuffix;
  	}

    public void transferFile( FileInputStream  fis, String uploadFileName, String uploadFileSuffix){
    	try {
    		      fos = new FileOutputStream(new File("/Users/qiwu/Downloads/" + uploadFileName
    		          + "new"+".")
    		          + uploadFileSuffix);
    		   //FileOutputStream fos = new FileOutputStream(new File("E:/images/"+uploadFileName+"new."+uploadFileSuffix));
    		   byte[] temp = new byte[1024];
    		   int i = fis.read(temp);
    		   while (i != -1){
    		       fos.write(temp,0,temp.length);
    		       fos.flush();
    		       i = fis.read(temp);
    		      }
    		   } catch (IOException e) {
    		      e.printStackTrace();
    		    } finally {
    		      if (fis != null) {
    		        try {
    		          fis.close();
    		        } catch (IOException e) {
    		          e.printStackTrace();
    		        }
    		      }
    		      
    		      if (fos != null) {
    		        try {
    		          fos.close();
    		        } catch (IOException e) {
    		          e.printStackTrace();
    		        }
    		      }
    		    }
    }
}
