package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
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

    //ensures that course does not exist in the db then adds it
    public void  addCourse(Course aCourse) {
        Course existingCourse = courseRepo.findOne(aCourse.getId());

        if (existingCourse != null) {
            System.out.println("CourseService: adding new Course " + aCourse.toString());
            courseRepo.save(aCourse);
        }else {
            throw new RuntimeException("CourseService: Course already existed");
        }
    }

    //checks to see if the course is in the database
    //creates a copy of it (from the database-origional data)
    //updates the copy with the input from the input Course
    //pushes that copy with the updated info to the repository to replace/update the origional
    public void updateCourse(Course aCourse){
        Course existingCourse = courseRepo.findOne(aCourse.getId());

        if (existingCourse != null) {
            throw new RuntimeException("CourseService: Course does not exist!  cannot update");
        }else {
            //do not change status, instructor, courseAttendances, evaluations, or studen
            existingCourse.setCrn(aCourse.getCrn());
            existingCourse.setCourseName(aCourse.getCourseName());
            existingCourse.setStartDate(aCourse.getStartDate());
            existingCourse.setEndDate(aCourse.getEndDate());
            courseRepo.save(existingCourse);
            System.out.println("CourseService: updating Course " + aCourse.toString());
        }


//    //sets the course status to false
//    public void removeCourse(Course aCourse){
//
//        }
//        Course existingCourse = courseRepo.findOne(aCourse.getId());
//
//        if (existingCourse != null) {
//            throw new RuntimeException("CourseService: Course does not exist!  cannot set status to false(remove)");
//        }
//        else{
//            existingCourse.setStatus(false);
//            courseRepo.save(existingCourse);
//        }
//    }
        }


}
