package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;



@Controller
@RequestMapping("/admin")
public class AdminController {
    //@Autowired
    //AttendanceService attendenceService;

    @Autowired
    CourseRepository courseRepo;




    @RequestMapping("/home")
    public String adminHome(Model model){


        model.addAttribute("allcourses", courseRepo.findAll());
        return "adminpages/adminhome";
    }

    @GetMapping("/addcourse")
    public String addCourse(Model model)
    {
        model.addAttribute("newcourse", new Course());
        return "adminpages/addcourse";
    }

    @PostMapping("/addcourse")
    public String postCourse(@ModelAttribute("newcourse")Course newcourse)
    {
        courseRepo.save(newcourse);

         return"redirect:/admin/addcourse";
    }
    @GetMapping("/updatecourse/{id}")
    public String editCourse(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newcourse",courseRepo.findOne(id));

        return "adminpages/addcourse";
    }

    @GetMapping("/detailsofacourse/{id}")
    public String displayCourse (@PathVariable("id")long id,
                                         Model model) {
        Course course = courseRepo.findOne(id);
        model.addAttribute("course", course);

        return "datailsofacourse";
    }




}
