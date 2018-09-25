/**
 * 
 */
package com.gimc.user.modules.b_openingbank.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 开户行管理Entity
 * @author gu
 * @version 2018-05-08
 */
public class Openingbank extends DataEntity<Openingbank> {
	
	private static final long serialVersionUID = 1L;
	private String openingbankLogoUrl;		// 开户银行logo
	private String openingbankName;		// 开户银行
	
	public Openingbank() {
		super();
	}

	public Openingbank(String id){
		super(id);
	}

	@Length(min=0, max=255, message="开户银行logo长度必须介于 0 和 255 之间")
	public String getOpeningbankLogoUrl() {
		return openingbankLogoUrl;
	}

	public void setOpeningbankLogoUrl(String openingbankLogoUrl) {
		this.openingbankLogoUrl = openingbankLogoUrl;
	}
	
	@Length(min=0, max=128, message="开户银行长度必须介于 0 和 128 之间")
	public String getOpeningbankName() {
		return openingbankName;
	}

	public void setOpeningbankName(String openingbankName) {
		this.openingbankName = openingbankName;
	}
	
}