package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    PersonRepository personRepo;

    @Autowired
    PersonService personService;

    @RequestMapping("/home")
    public String teacherHome(){
        return "teacherpages/teacherhome";
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
