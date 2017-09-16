package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course,Long> {
   Iterable<Course> findAllByCourseName(String courseName);
   Iterable<Course> findAllById(long id);

   //Search the courses by status (complete/false or active/true)
   Iterable<Course> findAllByStatus(boolean status);

   Course findByCourseName(String coursenames);
   Course findByCrn(long crn);
   Course findByStartDate(String startDate);



   Course findAllByCrn(long crn);
   Long findById(long id);


}
