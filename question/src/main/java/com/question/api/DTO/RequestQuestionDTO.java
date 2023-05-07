package com.question.api.DTO;

import java.util.List;


import com.question.api.entity.CodeSnippets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@ToString @Builder
public class RequestQuestionDTO {
	
	private String title;

	private String content;

	private List<CodeSnippets> codes;

	private List<String> imageUrls;
	
	private String userId;


}