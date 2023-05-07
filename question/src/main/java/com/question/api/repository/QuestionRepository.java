package com.question.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.question.api.entity.Question;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String>{

	
}
