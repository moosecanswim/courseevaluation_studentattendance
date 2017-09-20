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
