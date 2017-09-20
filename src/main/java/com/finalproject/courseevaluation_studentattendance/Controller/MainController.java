package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class MainController {
   @Autowired
   CourseService courseService;

    @Autowired
    PersonService personService;

    @RequestMapping("/")
    public String welcomePage(){

        return "welcomepage";
    }

    @RequestMapping("/viewcourses")
    public String viewCoursesInsecure(Model model){
        Iterable<Course> courseList = courseService.findAll();
        model.addAttribute("courseList",courseList);

        return "viewcourses";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/processlogin")
    public String processLogin(Principal p){
        //could have an issue when passing the roles in a string or if someone is assigned both roles
        System.out.println("the principal is " +p.getName());
        Person tempP = personService.findByUsername(p.getName());

        if(personService.personHasRole(tempP,"ADMIN")){
            //send them to the admin home
            System.out.println("Going to admin home");
            return "redirect:/admin/home";
        }
        else if(personService.personHasRole(tempP,"TEACHER")){
            //send them to teacher home
            System.out.println("Going to teacher home");
            return "redirect:/teacher/home";
        }
        System.out.println("Error, going home home");
        return "redirect:/";
    }


    // ========== Teacher Registration FORM ==========
    @RequestMapping(value="/registrationteacher", method = RequestMethod.GET)
    public String showRegistrationTeacherPage (Model model){
        model.addAttribute("newTeacher", new Person());
        return "registerteacher";
    }

    @RequestMapping(value="/registrationteacher", method = RequestMethod.POST)
    public String processRegistrationSeekerPage (@Valid @ModelAttribute("newTeacher") Person person,
                                                 BindingResult result, Model model)
    {
        if (result.hasErrors()){
            return "registerteacher";
        }
        else{
            personService.saveTeacher(person);
            model.addAttribute("message", "The new teacher account has been successfully created");
        }
        return "welcomepage";
    }


}
