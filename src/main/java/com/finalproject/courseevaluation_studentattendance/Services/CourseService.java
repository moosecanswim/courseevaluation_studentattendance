package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
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
    public CourseService(CourseRepository courseRepo){
        this.courseRepo=courseRepo;
    }

    public Iterable<Course> findAll(){
        return courseRepo.findAll();
    }


    public Course  addCourse(Course aCourse) {
        Course existingCourse = courseRepo.findByCourseName(aCourse.getCourseName());
        courseRepo.findByCourseName(aCourse.getCourseName());
        if (existingCourse != null) {
            throw new RuntimeException("Course already exists!");
        }
        System.out.println("Course Service: adding new Course " + aCourse.toString());

        return courseRepo.save(aCourse);
    }
}
