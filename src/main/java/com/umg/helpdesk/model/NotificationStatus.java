package com.umg.helpdesk.model;

public enum NotificationStatus {

	NotViewed(0),
	Viewed(1);
	
	private final int innerValue;
	
	NotificationStatus(int innerValue) {
		this.innerValue = innerValue;
	}
	
	public int getInnerValue() {
		return this.innerValue;
	}
	
}
