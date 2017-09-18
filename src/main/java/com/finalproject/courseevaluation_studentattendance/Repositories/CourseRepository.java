package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course,Long> {
   Iterable<Course> findAllByCourseNameLike(String courseName);
   Iterable<Course> findAllById(long id);

   //Search the courses by status (complete/false or active/true)
   Iterable<Course> findAllByStatus(boolean status);
   Iterable<Course> findByCrnAndStatus(long crn, Boolean status);
   Iterable<Course> findByStatusAndCourseNameContains(Boolean status, String courseName);
   Iterable<Course> findAllByCourseNameAndStatusTrue(String courseName);
   Iterable<Course> findAllByCourseNameAndStatusFalse(String courseName);


   //this may be an issue because several courses can have the same name
   Course findByCourseName(String coursenames);
   Course findByCrn(long crn);


   Course findByStartDate(String startDate);



   Course findAllByCrn(long crn);
   Long findById(long id);


}
