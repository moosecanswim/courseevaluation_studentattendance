package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import org.hibernate.validator.internal.constraintvalidators.hv.EANValidator;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.InvocationTargetException;

public interface EvaluationRepository extends CrudRepository<Evaluation,Long> {

Iterable<Evaluation> findAllById(long id);
Iterable<Evaluation> findEvaluationsByCourseEvaluation_Id(long id);



}
