package com.eyeai.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class FileuploadServiceImpl implements FileuploadService {
    private FileOutputStream fos = null;
    
    @Value("${com.eyeai.storepath}")
    private String storepath;
    
    private Logger logger =Logger.getLogger(getClass());
    
	@Override
	public String upload(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {


	    String uploadFilePath = multiReq.getFile("file1").getOriginalFilename();
    
	   // 截取上传文件的文件名
	    String uploadFileName = getFilename(uploadFilePath);
	    // 截取上传文件的后缀
	    String uploadFileSuffix =getFileSuffix(uploadFilePath);
	    // 获取上传文件的输入流
	    FileInputStream fis = (FileInputStream)multiReq.getFile("file1").getInputStream();
	 
	    String imageStore =storepath+uploadFileName+"_eyeai."+uploadFileSuffix;

	    if (fis!=null) {
    	    transferFile(fis,imageStore);
    	    logger.info("上传文件成功！");
    	    return imageStore;
      } else {
            System.out.println("上传文件为空");
      }
	
		return "";
	}

    @Override
    public  List<String> mutiupload(HttpServletRequest req) throws IOException{
    	//多文件上传
    	List<MultipartFile> files = ((MultipartHttpServletRequest) req).getFiles("file");
    	//返回上传成功文件位置
    	List<String> resList=new ArrayList<>();
	    MultipartFile file = null;
	    if(files==null) return resList;
	    if(files.size()!=2) return resList;
	    
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
		    String imageStore ="E:/images/"+uploadFileName+"_eyeai."+uploadFileSuffix;
	        if (!file.isEmpty()) {
	    	    transferFile(fis,imageStore);
	    	    resList.add(imageStore);
	    	    logger.info("多文件上传成功"+i);
	      } else {
	        logger.error("上传文件为空");
	        return null;
	      }
	    }
	    logger.info("多文件上传成功1");;
		return resList;
    }
    public String getFilename(String uploadFilePath){
  	    
    	
  	    // 1.截取上传文件的文件名
  	    String uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf('/') + 1, uploadFilePath.indexOf('.'));
  	    // 2.截取上传文件的后缀

  	    return uploadFileName;    
  	}
  	public String getFileSuffix(String uploadFilePath){
  	    String uploadFileSuffix = uploadFilePath.substring(
  		        uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
  		
  		return uploadFileSuffix;
  	}

    public void transferFile( FileInputStream  fis,String imageStore){
    	try {
    		 
    		   FileOutputStream fos = new FileOutputStream(new File(imageStore));
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
