package com.otp.OnlineTestPortal.Repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otp.OnlineTestPortal.model.QuestionBank;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public interface QuestionBankRepo extends JpaRepository<QuestionBank, Integer>
{

 @SuppressWarnings("unchecked")
default	List<QuestionBank> findQuestionbyYear(String year, int numberOfQuestion, EntityManager entityManager)
 {
	 String sqlquery = "select * from questionbank where year = :year ORDER By RAND() LIMIT "+numberOfQuestion;
	 Query query = entityManager.createNativeQuery(sqlquery,QuestionBank.class);
	 query.setParameter("year", year);
	 return query.getResultList();
 }

}
