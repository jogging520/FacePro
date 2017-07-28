package com.eyeai.service;

import java.util.List;

public interface GetFaceTokenService {
    
	List<String> getFaceTokenList(String  imageStore);
	String getFaceToken(String imageStore);
}
