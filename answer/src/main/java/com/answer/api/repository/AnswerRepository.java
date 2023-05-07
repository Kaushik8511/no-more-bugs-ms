package com.answer.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.answer.api.entity.Answer;

@Repository
public interface AnswerRepository extends MongoRepository<Answer, String>{
	List<Answer> findByQuestionId(String questionId);
}
