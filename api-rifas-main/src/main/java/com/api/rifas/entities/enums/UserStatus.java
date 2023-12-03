package com.api.rifas.entities.enums;

public enum UserStatus {
	
	TRUE(1),
	FALSE(2);

	private int code;

	private UserStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public static UserStatus valueOf(int code) {
		for (UserStatus value : UserStatus.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid UserStatus code");
	}
}
