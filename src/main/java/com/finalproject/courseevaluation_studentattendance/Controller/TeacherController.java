package com.finalproject.courseevaluation_studentattendance.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @RequestMapping("/home")
    public String teacherHome(){
        return "teacherpages/teacherhome";
    }

}
