package com.thefreshlystore.utilities;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtility {
	public String generatePasswordHash(String userPassword) {
		return BCrypt.hashpw(userPassword, BCrypt.gensalt());
	}

	public boolean isPasswordSame(String userPassword, String encryptedPassword) {
		if (userPassword == null) {
			return false;
		}
		if (encryptedPassword == null) {
			return false;
		}
		return BCrypt.checkpw(userPassword, encryptedPassword);
	}
}
