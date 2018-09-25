/**
 * 
 */
package com.gimc.user.modules.b_gym_img.entity;

import org.hibernate.validator.constraints.Length;

import com.gimc.user.common.persistence.DataEntity;

/**
 * 健身房图片Entity
 * @author linhaomiao
 * @version 2018-05-16
 */
public class GymImg extends DataEntity<GymImg> {
	
	private static final long serialVersionUID = 1L;
	private String gymId;		// 健身房id
	private String intro;		// 图片介绍
	private String url;		// 图片链接
	private String type;		// 图片类型
	private String seq;		// 排序
	
	public GymImg() {
		super();
	}

	public GymImg(String id){
		super(id);
	}

	@Length(min=0, max=11, message="健身房id长度必须介于 0 和 11 之间")
	public String getGymId() {
		return gymId;
	}

	public void setGymId(String gymId) {
		this.gymId = gymId;
	}
	
	@Length(min=0, max=64, message="图片介绍长度必须介于 0 和 64 之间")
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	@Length(min=0, max=256, message="图片链接长度必须介于 0 和 256 之间")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Length(min=0, max=2, message="图片类型长度必须介于 0 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
}