package com.techman.dto;

public class EngineerDTOImpl implements EngineerDTO {
	private String email;
    private String password;
    private String category;

    public EngineerDTOImpl() {}

    public EngineerDTOImpl(String email, String password, String category) {
        this.email = email;
        this.password = password;
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

	@Override
	public String toString() {
		return "Email=" + email + ", password=" + password + ", category=" + category + "]";
	}
    
    
}
