package com.techman.dto;

public interface ComplainStatusDTO {
	public int getCom_id();
	public void setCom_id(int com_id);
	public String getDepartment_name();
	public void setDepartment_name(String department_name);

	public String getSubtype();

	public void setSubtype(String subtype) ;

	public String getStatus();
	public void setStatus(String status);
	public String getEmail();
	public void setEmail(String email);
}
