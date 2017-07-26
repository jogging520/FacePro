package com.eyeai.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLException;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
public class PostServiceImpl implements PostService {

	@Override
	public String  postImage(String URL, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws IOException {
		HttpURLConnection conne;
        URL url1 = new URL(URL);
        conne = (HttpURLConnection) url1.openConnection();
        conne.setDoOutput(true);
        conne.setUseCaches(false);
        conne.setRequestMethod("POST");
        conne.setConnectTimeout(30000);
        conne.setReadTimeout(50000);
        conne.setRequestProperty("accept", "*/*");
        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + getBoundary());
        conne.setRequestProperty("connection", "Keep-Alive");
        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String, String> entry = (Map.Entry) iter.next();
            String key = entry.getKey();
            String value = entry.getValue();
            obos.writeBytes("--" + getBoundary() + "\r\n");
            obos.writeBytes("Content-Disposition: form-data; name=\"" + key + "\"\r\n");
            obos.writeBytes("\r\n");
            obos.writeBytes(value + "\r\n");
        }
        if(fileMap != null && fileMap.size() > 0){
            Iterator fileIter = fileMap.entrySet().iterator();
            while(fileIter.hasNext()){
                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
                obos.writeBytes("--" + getBoundary() + "\r\n");
                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
                        + "\"; filename=\"" + "UTF-8"+ "\"\r\n");
                obos.writeBytes("\r\n");
                obos.write(fileEntry.getValue());
                obos.writeBytes("\r\n");
            }
        }
        obos.writeBytes("--" + getBoundary() + "--" + "\r\n");
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
            return new String(new byte[0]);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[4096];
        int len;
        while((len = ins.read(buff)) != -1){
            baos.write(buff, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        String response = new String(bytes);
        System.out.println(response);
        ins.close();
        return response;
	}

	@Override
	public String postJson(JSONObject json, String URL) {
          try {
				URL url = new URL(URL);
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setDoOutput(true);
		        connection.setDoInput(true);
		        connection.setRequestMethod("POST");
		        connection.setUseCaches(false);
		        connection.setInstanceFollowRedirects(true);
		        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		        connection.connect();
		        //POST请求
		        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		        JSONObject obj = new JSONObject();
		        
		        out.writeBytes(obj.toString());
		        out.flush();
		        out.close();
		
		        //读取响应
		        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        String lines;
		        StringBuffer sb = new StringBuffer("");
		        while ((lines = reader.readLine()) != null) {
		            lines = new String(lines.getBytes(), "utf-8");
		            sb.append(lines);
		        }
		        System.out.println(sb);
		        reader.close();
		        // 断开连接
		        connection.disconnect();
		        return sb.toString();
		    } catch (MalformedURLException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (UnsupportedEncodingException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		    }
	   return "";
	}
    private static String getBoundary() {
	        StringBuilder sb = new StringBuilder();
	        Random random = new Random();
	        for(int i = 0; i < 32; ++i) {
	            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
	        }
	        return sb.toString();
	}
	
}
