package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import org.hibernate.validator.internal.constraintvalidators.hv.EANValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface EvaluationRepository extends CrudRepository<Evaluation,Long> {

Iterable<Evaluation> findAllById(long id);
Iterable<Evaluation> findEvaluationsByCourseEvaluation_Id(long id);
Page<Evaluation> findAll(Pageable pageable);


}
