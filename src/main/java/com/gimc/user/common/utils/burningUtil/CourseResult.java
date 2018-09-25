package com.gimc.user.common.utils.burningUtil;

import java.util.List;

import com.gimc.user.modules.b_movement_catalog.entity.MovementCatalog;
import com.gimc.user.modules.b_user_movement.entity.UserMovement;

public class CourseResult {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<UserMovement> list;
	private List<MovementCatalog> mcList;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<UserMovement> getList() {
		return list;
	}
	public void setList(List<UserMovement> list) {
		this.list = list;
	}
	public List<MovementCatalog> getMcList() {
		return mcList;
	}
	public void setMcList(List<MovementCatalog> mcList) {
		this.mcList = mcList;
	}
	
	
	
}
