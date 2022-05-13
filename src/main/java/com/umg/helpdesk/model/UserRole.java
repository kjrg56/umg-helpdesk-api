package com.umg.helpdesk.model;

public enum UserRole {

	Admin(0),
	Technician(1),
	Worker(2),
	Manager(3);
	
	private final int innerValue;
	
	UserRole(int innerValue) {
		this.innerValue = innerValue;
	}
	
	public int getInnerValue() {
		return this.innerValue;
	}
	
}
