package com.eyeai.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class FileuploadService {
    FileOutputStream fos = null;
    
    public void upload(String uploadFileName,String uploadFileSuffix,FileInputStream fis ){
		    try {
		//      fos = new FileOutputStream(new File("/Users/qiwu/Downloads/" + uploadFileName
		//          + "new"+".")
		//          + uploadFileSuffix);
		      FileOutputStream fos = new FileOutputStream(new File("E:/images/"+uploadFileName+"new."+uploadFileSuffix));
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
    public String getFilename(String uploadFilePath){

  	  System.out.println("uploadFlePath:" + uploadFilePath);
  	    
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

}
