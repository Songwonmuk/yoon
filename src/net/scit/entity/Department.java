package net.scit.entity;

/**
 * Department
 * �����μ�ǥ
 */
public enum Department {
	MI("�ܰ�"),
	NI("����"),
	SI("�Ǻΰ�"),
	TI("�Ҿư�"),
	VI("����ΰ�"),
	WI("�񴢱��");

	private String type;

	// Code Here
	Department(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
