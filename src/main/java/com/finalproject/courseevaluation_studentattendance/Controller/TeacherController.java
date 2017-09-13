package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
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

    @RequestMapping("/home")
    public String teacherHome(){
        return "teacherpages/teacherhome";
    }


    //this route can be combine with the teacher home page later
    @GetMapping("/listallcourses")
    public String listCourse(Principal p, Model model)
    {
        Person instructor = personRepository.findByUsername(p.getName());

        Iterable<Course> allCoursesofAInstructor = instructor.getCourseInstructor();

        model.addAttribute("allcoursesofaTeacher", allCoursesofAInstructor);

        return "listallcourses";
    }


    //will have a add student
    @GetMapping("/detailsofacourse/{id}")
    public String detailsofcourse(@PathParam("id") Long courseId, Model model)
    {

        Course currentCourse = courseRepository.findOne(courseId);
        model.addAttribute("course", currentCourse);

        return "detailsofacourse";
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
       personRepo.save(person);
       return "confirmstudent";
   }

   @RequestMapping("/displaystudents")
    public String displayStudents(@ModelAttribute("lstudents")Person person, Model model)
   {
        model.addAttribute("lstudents", personRepo.findAll());
        return "displaystudents";
   }



}
