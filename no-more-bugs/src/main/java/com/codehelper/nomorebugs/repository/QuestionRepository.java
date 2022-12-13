package com.codehelper.nomorebugs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codehelper.nomorebugs.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	@Query(value = "SELECT * FROM questions WHERE title LIKE ?1", nativeQuery = true)
	public Page<Question> customSearchQuery(String title, Pageable pageable);
}
