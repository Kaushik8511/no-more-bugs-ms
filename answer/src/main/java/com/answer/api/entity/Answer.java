package com.answer.api.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@ToString
public class Answer {
	@Id
	private String id;
	
	private String content;
	
	private List<CodeSnippets> codes;
	
	private List<String> imageUrl;
	
	private String userId;
	
	private String questionId;
	
	private List<String> ratingUserIds;
	
	private Date createdAt;
	
	private Date modifiedAt;
	
	
}
