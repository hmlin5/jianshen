/**
 * 
 */
package com.gimc.user.modules.b_movement_catalog.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gimc.user.common.persistence.DataEntity;
import com.gimc.user.modules.sys.entity.Menu;

/**
 * 动作分类Entity
 * @author linhaomiao
 * @version 2018-05-02
 */
public class MovementCatalog extends DataEntity<MovementCatalog> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 分类名称
	private String parentId;		// 上级分类id
	private String parentName;		// 上级分类名称
	private String level;		// 分类层级
	private String seq;		// 排序
	private String isShow;		// 是否显示
	private String catalogLevel;		// 分类级别, 一级或者二级
	private String bMovementCatalogField01;		// 预留字段1
	private String bMovementCatalogField02;		// 预留字段2
	
	public MovementCatalog() {
		super();
	}

	public MovementCatalog(String id){
		super(id);
	}

	
	@JsonIgnore
	public static void sortList(List<MovementCatalog> list, List<MovementCatalog> sourcelist, String parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			MovementCatalog e = sourcelist.get(i);
			if (e.getParentId()!=null && e.getParentId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						MovementCatalog child = sourcelist.get(j);
						if (child.getParentId()!=null && child.getParentId().equals(e.getId())){
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
	
	@Length(min=0, max=11, message="分类层级长度必须介于 0 和 11 之间")
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
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	@Length(min=0, max=1, message="分类级别, 一级或者二级长度必须介于 0 和 1 之间")
	public String getCatalogLevel() {
		return catalogLevel;
	}

	public void setCatalogLevel(String catalogLevel) {
		this.catalogLevel = catalogLevel;
	}
	
	@Length(min=0, max=64, message="预留字段1长度必须介于 0 和 64 之间")
	public String getBMovementCatalogField01() {
		return bMovementCatalogField01;
	}

	public void setBMovementCatalogField01(String bMovementCatalogField01) {
		this.bMovementCatalogField01 = bMovementCatalogField01;
	}
	
	@Length(min=0, max=64, message="预留字段2长度必须介于 0 和 64 之间")
	public String getBMovementCatalogField02() {
		return bMovementCatalogField02;
	}

	public void setBMovementCatalogField02(String bMovementCatalogField02) {
		this.bMovementCatalogField02 = bMovementCatalogField02;
	}
	
}