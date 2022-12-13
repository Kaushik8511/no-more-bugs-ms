package com.codehelper.nomorebugs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codehelper.nomorebugs.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
	List<Answer> findByQuestionId(long questionId);

}
