package com.codehelper.nomorebugs.payload;

public class JWTAuthResponse {
	private String accessToken;
	
	private String tokenType = "Bearer";
	
	private long userId;

	public JWTAuthResponse(String accessToken, long userId) {
		super();
		this.accessToken = accessToken;
		this.userId = userId;
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	
}
