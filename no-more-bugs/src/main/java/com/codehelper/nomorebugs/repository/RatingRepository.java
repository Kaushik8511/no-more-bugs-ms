package com.codehelper.nomorebugs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.codehelper.nomorebugs.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

	@Query(value = "SELECT * FROM RATINGS WHERE USER_ID = ?1 AND ANSWER_ID = ?2", nativeQuery = true)
	public Rating customFindQuery(long userId, long answerId);
}
