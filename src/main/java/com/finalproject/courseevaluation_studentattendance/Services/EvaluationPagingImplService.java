//package com.finalproject.courseevaluation_studentattendance.Services;
//
//import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
//import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationPagingService;
//import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.awt.print.Pageable;
//
//@Service
//@Transactional
//public class EvaluationPagingImplService implements EvaluationPagingService
//{
//
//
//    @Autowired
//    EvaluationRepository evaluationRepository;
//
//
//    EvaluationPagingImplService(EvaluationRepository evaluationRepository){
//       this.evaluationRepository = evaluationRepository;
//   }
//
//   @Override
//   public Page<Evaluation> listAllByPage(Pageable pageable) {
//       evaluationRepository.findAllBy((org.springframework.data.domain.Pageable) pageable);
//       return null;
//   }
//}
