package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

            courseRepo.save(aCourse);
        }
    }

    //sets the course status to false
    public void removeCourse(Course aCourse){
        Course existingCourse = courseRepo.findOne(aCourse.getId());

        if (existingCourse == null) {
            throw new RuntimeException("CourseService: Course does not exist!  cannot set status to false(remove)");
        }
        else{
            existingCourse.setStatus(false);
            courseRepo.save(existingCourse);
        }
    }

    //find course by crn
    public Course findByCRN(long crn){
        return courseRepo.findByCrn(crn);
    }

    public Course findByStartDate(String startDate)
    {
        return courseRepo.findByStartDate(startDate);
    }

    //remove a student from a course
    //take a temp set of students from the course and checks to see if the student is in it
    //if the student is in that set it will remove the student from the temp sent courseStudents
    //also we need to set the Course courseStudent for this Person as null
    //save the new set of student to the course
    public void removeStudentFromCourse (Course aCourse, Person aStudent){

        Set<Person> courseStudents = aCourse.getStudent();

        // Print all students (irst names) in the course before the removing of student:
        for(Person s: courseStudents){
            System.out.println("Students" +
                    " that are currently enrolled in the course: "
                    + s.getFirstName());
        }
        if(courseStudents.contains(aStudent)){

            System.out.println("CourseService/removeStudentFromCourse:remove students");

            aStudent.setCourseStudent(null);
            courseStudents.remove(aStudent);
            aCourse.setStudent(courseStudents);
            courseRepo.save(aCourse);

            // Print all students in the course after removing one:
            for(Person s: courseStudents){
                System.out.println("Students" +
                        " that are in the course after removing one student: "
                        + s.getFirstName());
            }
        }
        else{
            System.out.println("CourseService/removeStudentFromCourse: student was not part of class to begin with");
        }
    }

}
