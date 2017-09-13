package com.finalproject.courseevaluation_studentattendance.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/")
    public String welcomePage(){
        return "welcomepage";
    }
}
