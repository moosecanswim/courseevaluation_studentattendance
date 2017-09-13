package com.finalproject.courseevaluation_studentattendance.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Admin")
public class AdminController {

    @RequestMapping("/home")
    public String adminHome(Model model){

        return "adminpages/adminhome";
    }
}
