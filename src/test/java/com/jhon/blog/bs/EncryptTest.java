package com.jhon.blog.bs;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptTest {
	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodePasswd = encoder.encode("123456");
		System.out.println(encodePasswd);
	}
}
