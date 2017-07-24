package com.eysai.test;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class json {
	
	public static void main(String[] args) {    
	    String json="{\"name\":\"刘德华\",\"age\":35,\"some\":[{\"k1\":\"v1\",\"k2\":\"v2\"},{\"k3\":\"v3\",\"k4\":\"v4\"}]}";
	    
	    JSONObject jso=JSON.parseObject(json);//json字符串转换成jsonobject对象
	    System.out.println("初始jsonObject:\n"+jso+"\n");
	    JSONArray jsarr=jso.getJSONArray("some");//jsonobject对象取得some对应的jsonarray数组
	    System.out.println("jsonObject里面的jsonarray:\n"+jsarr+"\n");
	   JSONObject ao=jsarr.getJSONObject(0);//jsonarray对象通过getjsonobjext(index)方法取得数组里面的jsonobject对象
	   System.out.println("jsonObject里面的jsonarray里面的第一个jsonobject：\n"+ao+"\n");
	   String vString=ao.getString("k1");//jsonobject对象通过key直接取得String的值
	   System.out.println("jsonObject里面的jsonarray里面的第一个jsonobject里的键值对对k1取值：\n"+vString+"\n");
	   
	  ////////////////////////////
	   String json2="{\"message\":\"result\",\"status\":200,\"data\":{\"token\":\"A8E752FDA8bHmNOtbekBAICAFICCFUI\"}}";
	   JSONObject netjso=JSON.parseObject(json2);
	   System.out.println("初始netjso----jsonObject:\n"+netjso+"\n");
	   JSONObject datas=JSON.parseObject(netjso.getString("data"));
	   String mytoken=datas.getString("token");  
       System.out.println(mytoken);
       
       String json3="{\"image_id\": \"EB81MUR4RV+bXIkngv078Q==\", \"request_id\": \"1500890057,243910d1-bb8f-4686-b978-1d942ae01036\", \"time_used\": 332, \"faces\": [{\"face_rectangle\": {\"width\": 177, \"top\": 102, \"left\": 171, \"height\": 177}, \"face_token\": \"ddcbd9a6b1b3b26b9c42e9bf8496730c\"}]}";
	   JSONObject faceDetecReturn=JSON.parseObject(json3);
	   String faces=faceDetecReturn.getString("faces");
       List<HashMap> list =JSON.parseArray(faces, HashMap.class);  

	   System.out.println(list.get(0).get("face_token"));
	   

	}

}
