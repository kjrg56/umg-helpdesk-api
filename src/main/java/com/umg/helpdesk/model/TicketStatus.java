package com.umg.helpdesk.model;

public enum TicketStatus {

	Pending(0),
	Assigned(1),
	InProgress(2),
	Closed(3);
	
	private final int innerValue;
	
	TicketStatus(int innerValue) {
		this.innerValue = innerValue;
	}
	
	public int getInnerValue() {
		return this.innerValue;
	}
	
}
