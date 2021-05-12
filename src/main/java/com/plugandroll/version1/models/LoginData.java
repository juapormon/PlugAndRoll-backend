package com.plugandroll.version1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginData {
	
	@NotBlank
	private String username;
	@NotBlank
	private String password;

}
