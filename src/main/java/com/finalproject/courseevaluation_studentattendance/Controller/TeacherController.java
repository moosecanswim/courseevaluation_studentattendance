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

   @GetMapping("/addstudent")
    public String getCourse(Model model)
   {
       model.addAttribute("newstudent", new Person());
       return "addstudent";
   }

   @PostMapping("/addstudent")
    public String postCourse(@ModelAttribute("newstudent")Person person, Model model)
   {

       model.addAttribute("newstudent", person);
       personRepo.save(person);
       return "confirmstudent";
   }

   @RequestMapping("/displaystudents")
    public String displayStudents(@ModelAttribute("lstudents")Person person, Model model)
   {
       personService.findAll();
       return "displaystudents";
   }



}
