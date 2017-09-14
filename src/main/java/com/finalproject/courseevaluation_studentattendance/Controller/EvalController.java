package com.finalproject.courseevaluation_studentattendance.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eval")
public class EvalController {
    @RequestMapping("/home")
    public String evalHome(){
        return "/evalpages/evalhome";
    }
}
