package hu.webuni.university.dto;

import lombok.Data;

@Data
public class LoginDto {

	private String username;
	private String password;
	private String fbToken;
	private String googleToken;
}
