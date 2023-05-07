package com.question.api.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ToString @Builder
public class Question {
	
	@Id
	private String id;

	@Indexed(unique = true)
	private String title;

	private String content;

	private List<CodeSnippets> codes;

	private List<String> imageUrls;
	
	private List<String> ratingUserIds;
	
	private String userId;
	
	private Date createdAt;
	
	private Date modifiedAt;


}
