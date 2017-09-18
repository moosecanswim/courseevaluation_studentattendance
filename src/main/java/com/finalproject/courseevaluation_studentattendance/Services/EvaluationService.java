package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.*;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

//// method adds crn of the course to the evaluation repository
  public void addEvalToCourse(Evaluation eval, Course cr)
  {
    Course eCourse = courseRepo.findOne(cr.getId());
    if(eCourse==null){
        System.out.println("Course Does not exist in the database so you may not add an evaluation to it");
    }
    else{
        eCourse.addEvaluation(eval);
        eval.setCourseEvaluation(eCourse);
    }
    //
     //eval.setId(cr.getId());

      //System.out.println(cr.getId());
     // System.out.println(eval.getId());


      //


//      cr.addEvaluation(eval);
//
//        eval.setCourseEvaluation(cr);




    //  eval.setId(crs.getId());

     //
     // eval.setId(cr.getId());



    evaluationRepo.save(eval);

    System.out.println(cr.getId());

    System.out.println(eval.getId());
  }

  public Iterable<Evaluation> matchCourseToEvals(Course cr)
  {
      Course course = courseRepo.findOne(cr.getId());
      course.getEvaluations();
      return evaluationRepo.findEvaluationsByCourseEvaluation_Id(course.getId());

  }

    public Evaluation SaveEntry(Evaluation aValuation)
    {
        return evaluationRepo.save(aValuation);
    }
}
