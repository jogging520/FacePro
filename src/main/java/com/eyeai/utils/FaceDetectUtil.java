package com.eyeai.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.net.ssl.SSLException;

public class FaceDetectUtil {
    private final static int CONNECT_TIME_OUT = 30000;
    private final static int READ_OUT_TIME = 50000;
    private static String boundaryString = getBoundary();
    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
        HttpURLConnection conne;
        URL url1 = new URL(url);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(CONNECT_TIME_OUT);
        conne.setReadTimeout(READ_OUT_TIME);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + boundaryString + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
                    + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + boundaryString + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
        obos.writeBytes("\r\n");
        obos.flush();
        obos.close();
        InputStream ins = null;
        int code = conne.getResponseCode();
        try{
            if(code == 200){
                ins = conne.getInputStream();
            }else{
                ins = conne.getErrorStream();
            }
        }catch (SSLException e){
            e.printStackTrace();
            return new byte[0];
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        ins.close();
        return bytes;
    }
    private static String getBoundary() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 32; ++i) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
        }
        return sb.toString();
    }
    private static String encode(String value) throws Exception{
        return URLEncoder.encode(value, "UTF-8");
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

    public String faceDetectapi(byte[]imagebuffer,String url,Map<String,String> map){
    	
    	return null;
    }
    public static void main(String[] args) throws Exception{
  
        //File file = new File("/Users/qiwu/Downloads/baby.JPG"); 
    	//E\://images/test1.jpg 
    	/*
    	 * {"image_id": "EB81MUR4RV+bXIkngv078Q==", "request_id": "1500890057,243910d1-bb8f-4686-b978-1d942ae01036", "time_used": 332, "faces": [{"face_rectangle": {"width": 177, "top": 102, "left": 171, "height": 177}, "face_token": "ddcbd9a6b1b3b26b9c42e9bf8496730c"}]}

    	 */
    	File file = new File("E:/images/testimage.jpg"); 
    	//System.out.println("dfdfdfdf");
        byte[] buff = getBytesFromFile(file);
        String urldetect = "https://api-cn.faceplusplus.com/facepp/v3/detect";
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> byteMap = new HashMap<>();
        map.put("api_key", "EmwiWkkws71IqE1zQbdjkATEHgGlBeSq");
        map.put("api_secret", "p0kdAQ8heNM5_sM7H0_0Gex4ZV6exHO3");
        byteMap.put("image_file", buff);
        try{
            byte[] bacd = post(urldetect, map, byteMap);
            String str = new String(bacd);
            System.out.println(str);
        }catch (Exception e) {
         e.printStackTrace();
        }
   }
  ///////////////////////////////////////////////
//       String urlCompare = "https://api-cn.faceplusplus.com/facepp/v3/compare";
//       HashMap<String, byte[]> byteMap = new HashMap<>();
//	   HashMap<String, String> map = new HashMap<>();
//       map.put("api_key", "EmwiWkkws71IqE1zQbdjkATEHgGlBeSq");
//       map.put("api_secret", "p0kdAQ8heNM5_sM7H0_0Gex4ZV6exHO3");
//       try{
//           byte[] bacd = post(urldetect, map, byteMap);
//           String str = new String(bacd);
//           System.out.println(str);
//       }catch (Exception e) {
//        e.printStackTrace();
//       }
//  
//    }	
}
