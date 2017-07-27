package com.eyeai.service;

import java.io.IOException;

public interface FaceSetService {

	 public String createFaceSet() throws IOException;
	 public void addFaceSet(String faceset_token,String face_token);
	 public void removeFaceSet(String faceset_token,String face_token);
	 
}
