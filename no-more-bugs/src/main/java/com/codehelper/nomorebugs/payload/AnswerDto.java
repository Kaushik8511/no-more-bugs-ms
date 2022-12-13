package com.codehelper.nomorebugs.payload;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AnswerDto {

	private long id;

	@NotEmpty
	@Size(min = 5, message = "Question content must have atleast 5 characters")	
	private String content;
	
	private String code;
	
	private String imageUrl;
	
	private long rating;
	
	@NotNull(message = "provide user id")
	@Min(value = 1, message = "Not provided user id or invalid user id")
	private long userId;
	
	//can delete later
	private long questionId;
	
	public AnswerDto() {}

	public AnswerDto(long id, String content, String code, String imageUrl, long rating, long user_id,
			long question_id) {
		this.id = id;
		this.content = content;
		this.code = code;
		this.imageUrl = imageUrl;
		this.rating = rating;
		this.userId = user_id;
		this.questionId = question_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "AnswerDto [id=" + id + ", content=" + content + ", code=" + code + ", imageUrl=" + imageUrl
				+ ", rating=" + rating + ", userId=" + userId + ", questionId=" + questionId + "]";
	}
}
