package com.eyeai.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

public interface PostService {
     
	public String postImage(String URL,HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws IOException;
	
	public String postJson(JSONObject json,String url) throws IOException;

	public byte[] getBytesFromFile(File f);
	
}
