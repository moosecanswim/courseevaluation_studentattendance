package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {
    @Autowired
    AttendanceRepository attendenceRepo;
    @Autowired
    CourseRepository courseRepo;
    @Autowired
    EvaluationRepository evaluationRepo;
    @Autowired
    PersonRepository personRepo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    public EvaluationService(EvaluationRepository evalRepo){
        this.evaluationRepo=evalRepo;
    }


  public void addEvalToCourse(Course cr, Evaluation eval)
  {
     cr.getCourseName();
     eval.setCourseEvaluation(cr);
     evaluationRepo.save(eval);
  }

    public Evaluation SaveEntry(Evaluation aValuation)
    {
        return evaluationRepo.save(aValuation);
    }
}
