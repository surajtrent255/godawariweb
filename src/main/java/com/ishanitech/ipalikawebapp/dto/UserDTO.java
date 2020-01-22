package com.ishanitech.ipalikawebapp.dto;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 2989312298454528014L;
	private int userId;
	private String username;
	private String fullName;
	private String email;
	private String password;
	private String mobileNumber;
	private boolean isLocked;
	private boolean enabled;
	private boolean firstLogin;
	private boolean expired;
	private List<String> roles;
}
