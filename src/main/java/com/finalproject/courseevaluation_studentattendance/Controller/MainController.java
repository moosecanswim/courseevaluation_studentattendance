package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
   @Autowired
   CourseService courseService;

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
}
