package net.scit.entity;

/**
 * Department
 * 진찰부서표
 */
public enum Department {
	MI("외과"),
	NI("내과"),
	SI("피부과"),
	TI("소아과"),
	VI("산부인과"),
	WI("비뇨기과");

	private String type;

	// Code Here
	Department(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
