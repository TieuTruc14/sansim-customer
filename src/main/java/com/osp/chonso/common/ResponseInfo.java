package com.osp.chonso.common;

import java.io.Serializable;

public class ResponseInfo implements Serializable{
	private int responseCode=-1;
	private String description = "";
	private Object content = null;
	public int getResponseCode(){
		return responseCode;
	}
	public String getDescription(){
		return description;
	}
	public Object getContent(){
		return content;
	}
	public void setResponseCode(int responseCode){
		this.responseCode=responseCode;
	}
	public void setDescription(String description){
		this.description = description;
	}

	public void setContent(Object content){
		this.content = content;
	}
}