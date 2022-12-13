package com.codehelper.nomorebugs.service;



import com.codehelper.nomorebugs.payload.QuestionDto;
import com.codehelper.nomorebugs.payload.QuestionsResponse;

public interface QuestionService {
	
	QuestionDto createQuestion(QuestionDto questionDto);
	
	QuestionsResponse getAllQuestions(int pageNo, int pageSize, String soryBy, String sortDir);
	
	QuestionsResponse searchQuestions(String query, int pageNo, int pageSize, String soryBy, String sortDir);
	
	QuestionDto getQuestionById(long id);
	
	QuestionDto updateQuestion(QuestionDto questionDto, long id);
	
	void deletePost(long id);
}
