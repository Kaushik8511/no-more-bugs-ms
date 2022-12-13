package com.codehelper.nomorebugs.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class QuestionDto {

	private long id;

	@NotEmpty
	@Size(min = 2, message = "Question title must have atleast 2 characters")
	private String title;

	@NotEmpty
	@Size(min = 5, message = "Question content must have atleast 5 characters")
	private String content;

	private String code;

	private String imageUrl;
	
	private long userId;
	
	private long rating;
	

	public QuestionDto() {
	}

	public QuestionDto(long id, String title, String content, String code, String imageUrl, long rating) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.code = code;
		this.imageUrl = imageUrl;
		this.rating = rating;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public long getRating() {
		return rating;
	}

	public void setRating(long rating) {
		this.rating = rating;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
}
