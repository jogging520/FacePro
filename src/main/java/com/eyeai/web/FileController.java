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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class FileController {
	@RequestMapping(value = "/testUploadFile", method = RequestMethod.POST)
	  public void testUploadFile(HttpServletRequest req,
	      MultipartHttpServletRequest multiReq) {
	    // 获取上传文件的路径
	    String uploadFilePath = multiReq.getFile("file1").getOriginalFilename();
	    System.out.println("uploadFlePath:" + uploadFilePath);
	    // 截取上传文件的文件名
	    String uploadFileName = uploadFilePath.substring(
	        uploadFilePath.lastIndexOf('/') + 1, uploadFilePath.indexOf('.'));
	    System.out.println("multiReq.getFile()" + uploadFileName);
	    // 截取上传文件的后缀
	    String uploadFileSuffix = uploadFilePath.substring(
	        uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
	    System.out.println("uploadFileSuffix:" + uploadFileSuffix);
	    FileOutputStream fos = null;
	    FileInputStream fis = null;
	    try {
	      fis = (FileInputStream) multiReq.getFile("file1").getInputStream();
	      fos = new FileOutputStream(new File("/Users/qiwu/Downloads/" + uploadFileName
	          + "new"+".")
	          + uploadFileSuffix);
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

	@RequestMapping(value = "testUploadFiles", method = RequestMethod.POST)
	  @ResponseBody
	  public void handleFileUpload(HttpServletRequest request) {
	    List<MultipartFile> files = ((MultipartHttpServletRequest) request)
	        .getFiles("file");
	    MultipartFile file = null;
	    BufferedOutputStream stream = null;
	    for (int i = 0; i < files.size(); ++i) {
	      file = files.get(i);
	      if (!file.isEmpty()) {
	        try {
	          String uploadFilePath = file.getOriginalFilename();
	          System.out.println("uploadFlePath:" + uploadFilePath);
	          // 截取上传文件的文件名
	          String uploadFileName = uploadFilePath
	              .substring(uploadFilePath.lastIndexOf('/') + 1,
	                  uploadFilePath.indexOf('.'));
	          System.out.println("multiReq.getFile()" + uploadFileName);
	          // 截取上传文件的后缀
	          String uploadFileSuffix = uploadFilePath.substring(
	              uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
	          System.out.println("uploadFileSuffix:" + uploadFileSuffix);
	          stream = new BufferedOutputStream(new FileOutputStream(new File(
	              ".//uploadFiles//" + uploadFileName + "." + uploadFileSuffix)));
	          byte[] bytes = file.getBytes();
	          stream.write(bytes,0,bytes.length);
	        } catch (Exception e) {
	          e.printStackTrace();
	        } finally {
	          try {
	            if (stream != null) {
	              stream.close();
	            }
	          } catch (IOException e) {
	            e.printStackTrace();
	          }
	        }
	      } else {
	        System.out.println("上传文件为空");
	      }
	    }
	    System.out.println("文件接受成功了");
	  }
	@RequestMapping(value = "/testDownload", method = RequestMethod.GET)
	  public void testDownload(HttpServletResponse res) {
	    String fileName = "upload.jpg";
	    res.setHeader("content-type", "application/octet-stream");
	    res.setContentType("application/octet-stream");
	    res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
	    byte[] buff = new byte[1024];
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    try {
	      os = res.getOutputStream();
	      bis = new BufferedInputStream(new FileInputStream(new File("d://"
	          + fileName)));
	      int i = bis.read(buff);
	      while (i != -1) {
	        os.write(buff, 0, buff.length);
	        os.flush();
	        i = bis.read(buff);
	      }
	    } catch (IOException e) {
	      e.printStackTrace();
	    } finally {
	      if (bis != null) {
	        try {
	          bis.close();
	        } catch (IOException e) {
	          e.printStackTrace();
	        }
	      }
	    }
	    System.out.println("success");
	  }
	
}
