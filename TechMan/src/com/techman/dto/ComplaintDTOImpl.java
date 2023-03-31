package com.techman.dto;

public class ComplaintDTOImpl implements ComplaintDTO{
	private int type;
	private String sub_type;
	
	public ComplaintDTOImpl() {}
	
	public ComplaintDTOImpl(int type, String sub_type) {
		this.type = type;
		this.sub_type = sub_type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSub_type() {
		return sub_type;
	}

	public void setSub_type(String sub_type) {
		this.sub_type = sub_type;
	}

	@Override
	public String toString() {
		return "Type=" + type + ", sub_type=" + sub_type + "]";
	}

	
	
	
}
