package com.finalproject.courseevaluation_studentattendance.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
public class StudentController {
    @RequestMapping("/home")
    public String studentHome(){
        return "studentpages/studenthome";
    }
}

