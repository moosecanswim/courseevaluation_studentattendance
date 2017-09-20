package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EvaluationPaging extends PagingAndSortingRepository<Evaluation, Long>
 {
}
