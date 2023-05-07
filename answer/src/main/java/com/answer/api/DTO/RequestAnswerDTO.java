package com.answer.api.DTO;

import java.util.List;

import com.answer.api.entity.CodeSnippets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor @Builder
public class RequestAnswerDTO {

	private String content;
	
	private List<CodeSnippets> codes;
	
	private List<String> imageUrl;
		
	private String userId;
	
	
}
