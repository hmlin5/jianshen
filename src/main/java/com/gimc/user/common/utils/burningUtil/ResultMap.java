package com.gimc.user.common.utils.burningUtil;

public class ResultMap {
	
	private String state;
	private String msg;
	private Object data;
	
	
	
	public String getState() {
		return state;
	}
	public ResultMap setState(String state) {
		this.state = state;
		return this;
	}
	public String getMsg() {
		return msg;
	}
	public ResultMap setMsg(String msg) {
		this.msg = msg;
		return this;
	}
	public Object getData() {
		return data;
	}
	public ResultMap setData(Object data) {
		this.data = data;
		return this;
	}
	
	
	
}
