package com.techman.dto;

public class ComplainStatusDTOImpl implements ComplainStatusDTO{
//	com_id | department_name | subtype | status | email
	private int com_id;
	private String department_name;
	private String subtype;
	private String status;
	private String email;
	
	public ComplainStatusDTOImpl() {}
	
	public ComplainStatusDTOImpl(int com_id, String department_name, String subtype, String status, String email) {
		super();
		this.com_id = com_id;
		this.department_name = department_name;
		this.subtype = subtype;
		this.status = status;
		this.email = email;
	}

	public int getCom_id() {
		return com_id;
	}

	public void setCom_id(int com_id) {
		this.com_id = com_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Com_id: " + com_id + " Department: " + department_name + " subtype :"
				+ subtype + " status: " + status + " Assigned: " + email + "\n";
	}
	
	
}
