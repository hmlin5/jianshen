package com.gimc.user.common.enums;

/**
 * 页面配置类型
 */
public enum PageConfigType {
	
	SLIDE(1,"轮播图"),
	TRADITIONAL_MEDIA(2,"热门传统媒体"),
	NETWORK_MEDIA(3,"热门网络媒体"),
	IP_PROJECT(4,"IP及专案资源推荐"),
	OWN_RESOURCES(5,"自有资源推荐"),
	MEDIA_PLANNING_CASE(6,"媒介策划案推荐"),
	RESOURCE_PRODUCT_LIBRARY(7,"资源产品库"),;

	
	private int key;
	private String value;
	
	PageConfigType(int key, String value) {
		this.key = key;
		this.value = value;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public static String get(int key){
		for (PageConfigType e : PageConfigType.values()) {
			if (e.getKey() == key) {
				return e.getValue();
			}
		}
		return null;
	}
	
}
