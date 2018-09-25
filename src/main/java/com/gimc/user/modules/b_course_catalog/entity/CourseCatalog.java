/**
 * 
 */
package com.gimc.user.modules.b_course_catalog.entity;

import java.util.List;

import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gimc.user.common.persistence.DataEntity;

/**
 * 课程分类Entity
 * @author linhaomiao
 * @version 2018-04-30
 */
public class CourseCatalog extends DataEntity<CourseCatalog> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 分类名称
	private String parentId;		// 上级分类id
	private String parentName;		// 上级分类名称
	private String level;		// 分类级别
	private String seq;		// 排序
	private String isShow;		// 是否显示
	private String isCatalog;		// 是否未分类
	private String bCourseCatalogField01;		// 预留字段1
	private String bCourseCatalogField02;		// 预留字段2
	
	public CourseCatalog() {
		super();
	}

	public CourseCatalog(String id){
		super(id);
	}

	
	
	@JsonIgnore
	public static void sortList(List<CourseCatalog> list, List<CourseCatalog> sourcelist, String parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			CourseCatalog e = sourcelist.get(i);
			if (e.getParentId()!=null && e.getParentId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						CourseCatalog child = sourcelist.get(j);
						if (child.getParentId()!=null && child.getParentId().toString().equals(e.getId())){
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}
	
	
	@JsonIgnore
	public static String getRootId(){
		return "0";
	}
	
	
	
	
	@Length(min=0, max=64, message="分类名称长度必须介于 0 和 64 之间")
	@NotBlank(message="分类名称不能为空")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	@Length(min=0, max=64, message="上级分类名称长度必须介于 0 和 64 之间")
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	@Length(min=0, max=11, message="分类级别长度必须介于 0 和 11 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	@Length(min=0, max=11, message="排序长度必须介于 0 和 11 之间")
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	@Length(min=0, max=1, message="是否显示长度必须介于 0 和 1 之间")
	@NotBlank(message="是否显示不能为空")
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	@Length(min=0, max=1, message="是否未分类长度必须介于 0 和 1 之间")
	public String getIsCatalog() {
		return isCatalog;
	}

	public void setIsCatalog(String isCatalog) {
		this.isCatalog = isCatalog;
	}
	
	@Length(min=0, max=64, message="预留字段1长度必须介于 0 和 64 之间")
	public String getBCourseCatalogField01() {
		return bCourseCatalogField01;
	}

	public void setBCourseCatalogField01(String bCourseCatalogField01) {
		this.bCourseCatalogField01 = bCourseCatalogField01;
	}
	
	@Length(min=0, max=64, message="预留字段2长度必须介于 0 和 64 之间")
	public String getBCourseCatalogField02() {
		return bCourseCatalogField02;
	}

	public void setBCourseCatalogField02(String bCourseCatalogField02) {
		this.bCourseCatalogField02 = bCourseCatalogField02;
	}
	
}