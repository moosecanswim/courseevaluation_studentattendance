package com.finalproject.courseevaluation_studentattendance.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/teacher")
public class TeacherController {
    @RequestMapping("/home")
    public String teacherHome(){
        return "teacherpages/teacherhome";
    }

}
