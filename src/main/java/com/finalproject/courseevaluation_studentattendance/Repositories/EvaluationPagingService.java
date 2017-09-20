package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EvaluationPagingService {
    Page<Evaluation> listAllByPage(Pageable pageable);
}
