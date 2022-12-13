package com.codehelper.nomorebugs.payload;

public class RatingDto {
	private long id;
	
	private long userId;
	
	private int value;

	public RatingDto() {}
	
	public RatingDto(long id, long userId, int value) {
		this.id = id;
		this.userId = userId;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "RatingDto [id=" + id + ", userId=" + userId + ", value=" + value + "]";
	}
	
	
	
	
}
