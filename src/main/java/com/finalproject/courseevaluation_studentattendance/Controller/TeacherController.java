package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.*;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.Date;


import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    PersonRepository personRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    PersonRepository personRepo;

    @Autowired
    PersonService personService;

    @Autowired
    CourseService courseService;

    @Autowired
    EvaluationRepository evaluationRepository;

    @RequestMapping("/home")
    public String teacherHome(Principal p, Model model)
    {
        model.addAttribute("instructor", personRepository.findByUsername(p.getName()));
        return "teacherhome";
    }


    //this route can be combine with the teacherhome page later
    @GetMapping("/listallcourses")
    public String listCourse(Principal p, Model model)
    {
        Person instructor = personRepository.findByUsername(p.getName());

        Iterable<Course> allCoursesofAInstructor = instructor.getCourseInstructor();

        model.addAttribute("allcoursesofaInstructor", allCoursesofAInstructor);

        return "listallcourses";
    }


    //will have a add student, set date for a course
    @GetMapping("/detailsofacourse/{id}")
    public String detailsofcourse(@PathParam("id") Long courseId, Model model)
    {

        Course currentCourse = courseRepository.findOne(courseId);
        Iterable<Person> studentsofACourse = currentCourse.getStudent();

        Iterable<Attendance> attendanceSheet = currentCourse.getCourseAttendances();

        //add a new attendance and set date (for a course)
        Date now= new Date();

        Attendance oneAttendancecourse = new Attendance();
        Attendance oneAttendance = new Attendance();

        oneAttendancecourse.setDate(now);
        oneAttendance.setDate(now);

        currentCourse.addAttendance(oneAttendancecourse);

                for (Person student : studentsofACourse) {
            student.addAttendance(oneAttendance);
        }


        model.addAttribute("course", currentCourse);
        model.addAttribute("studentsofACourse", studentsofACourse);
        model.addAttribute("AttendanceSheet", attendanceSheet);
        model.addAttribute("now", now);


        return "detailsofacourse";
    }


    //add an attendance and set date for each student of a course
    @GetMapping("/attendanceofacourse/{courseId}/{studentId}")
    public String listattendanceofacourse(@PathParam("courseId") Long courseId, @PathParam("studentId") Long studentId, Model model)
    {

       Course currentCourse = courseRepository.findOne(courseId);

        Iterable<Person> studentsofACourse = currentCourse.getStudent();

        Attendance oneStudentAttendance = new Attendance();
        Date now= new Date();

        oneStudentAttendance.setDate(now);
        Person student = personService.findById(studentId);
        //Person student = personRepo.findOne(studentId);
        student.addAttendance(oneStudentAttendance);


        model.addAttribute("course", currentCourse);
        model.addAttribute("studentsofACourse", studentsofACourse);
        model.addAttribute("student", student);

        return "attendanceofacourse";
    }

   @GetMapping("/addstudentstocourse/{id}")
    public String getCourse(@PathParam("id")Long id, Model model)
   {
       model.addAttribute("newstudent", new Person());
       return "addstudent";
   }

   @PostMapping("/addstudent/{id}")
    public String postCourse(@PathParam("id") Long id, Person person, Model model)
   {

       model.addAttribute("newstudent", person);
       personService.create(person);
      // personRepo.save(person);
       return "confirmstudent";
   }

   @RequestMapping("/displaystudents")
    public String displayStudents(@ModelAttribute("lstudents")Person person, Model model)
   {
        model.addAttribute("lstudents", personRepo.findAll());
        return "displaystudents";
   }


   @GetMapping("/evaluation/{id}")
    public String getEvaluation(@PathParam("id")Long id, Model model)
   {
    model.addAttribute("neweval", new Evaluation());
    return "evaluation";
   }
   @PostMapping("/evaluation/{id}")
   public String postEvaluation(@PathParam("id")Long id,Evaluation evaluation, Model model)
   {
       model.addAttribute("neweval", evaluation);
       evaluationRepository.save(evaluation);
       return "evaluation";
   }


}
