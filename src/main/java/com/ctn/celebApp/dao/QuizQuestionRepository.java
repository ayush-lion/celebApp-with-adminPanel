package com.ctn.celebApp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctn.celebApp.entity.QuizQuestion;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, String> {

}
