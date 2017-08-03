package com.eyeai.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public class FileuploadServiceImpl implements FileuploadService {
    FileOutputStream fos = null;
    
	@Override
	public String upload(HttpServletRequest req, MultipartHttpServletRequest multiReq) throws IOException {


	    String uploadFilePath = multiReq.getFile("file1").getOriginalFilename();
    
	   // 截取上传文件的文件名
	    String uploadFileName = getFilename(uploadFilePath);
	    // 截取上传文件的后缀
	    String uploadFileSuffix =getFileSuffix(uploadFilePath);
	    // 获取上传文件的输入流
	    FileInputStream fis = (FileInputStream)multiReq.getFile("file1").getInputStream();

	 
	    String imageStore ="E:/images/"+uploadFileName+"_eyeai."+uploadFileSuffix;
	    //String imageStore ="/Users/qiwu/Downloads/image/"+uploadFileName+"_eyeai."+uploadFileSuffix;

	    if (fis!=null) {
    	    transferFile(fis,uploadFileName,uploadFileSuffix,imageStore);
    	    return imageStore;
      } else {
            System.out.println("上传文件为空");
      }
	
		return "";
	}

    @Override
    public  List<String> mutiupload(HttpServletRequest req) throws IOException{
    	List<MultipartFile> files = ((MultipartHttpServletRequest) req).getFiles("file");
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
	    	    transferFile(fis,uploadFileName,uploadFileSuffix,imageStore);
	    	    resList.add(imageStore);
	    	    System.out.println("多文件上传");
	      } else {
	        System.out.println("上传文件为空");
	        return null;
	      }
	    }
	    System.out.println("多文件上传成功了");
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

    public void transferFile( FileInputStream  fis, String uploadFileName, String uploadFileSuffix,String imageStore){
    	try {
//    		      fos = new FileOutputStream(new File("/Users/qiwu/Downloads/" + uploadFileName
//    		          + "new"+".")
//    		          + uploadFileSuffix);
    		 
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
