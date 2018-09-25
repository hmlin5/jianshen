package com.gimc.user.modules.sys.entity;


public class RestResponseModel {
    private String ret = "";
    private String msg = "";
    private Object data = null;

    public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public RestResponseModel() {}

    public RestResponseModel(Object data) {
        this.data = data;
    }

    public RestResponseModel(String errcode, String errm) {
        this.ret = errcode;
        this.msg = errm;
    }

    public RestResponseModel(String errcode, String errm, Object data) {
        this.ret = errcode;
        this.msg = errm;
        this.data = data;
    }

}
