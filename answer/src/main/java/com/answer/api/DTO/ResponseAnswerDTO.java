package com.answer.api.DTO;

import java.util.Date;
import java.util.List;

import com.answer.api.entity.CodeSnippets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
public class ResponseAnswerDTO {

	private String id;
	
	private String content;
	
	private List<CodeSnippets> codes;
	
	private List<String> imageUrl;
	
	private String userId;
	
	private String questionId;
	
	private long ratings;
	
	private Date createdAt;
	
	private Date modifiedAt;
	
}
