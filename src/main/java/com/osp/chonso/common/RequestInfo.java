package com.osp.chonso.common;
import java.io.Serializable;

public class RequestInfo implements Serializable{
	private String command = "";
	private String password = "";
	private Object content = null;

	public Object getParam(){
		return content;
	}
	public void setParam(Object content){
		this.content = content;
	}

	public void setCommand(String command){
		this.command = command;
	}
	public String getCommand(){
		return command;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	
}