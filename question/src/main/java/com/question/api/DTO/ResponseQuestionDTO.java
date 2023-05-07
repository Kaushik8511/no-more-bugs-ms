package com.question.api.DTO;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.question.api.entity.CodeSnippets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ToString @Builder
public class ResponseQuestionDTO {
	
	private String id;

	private String title;

	private String content;

	private List<CodeSnippets> codes;

	private List<String> imageUrls;
	
	private long ratings;
	
	private String userId;
	
	private Date createdAt;
	
	private Date modifiedAt;


}