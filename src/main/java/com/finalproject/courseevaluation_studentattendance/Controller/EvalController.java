package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.Date;

@Controller
@RequestMapping("/eval")
public class EvalController {
    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    EvaluationService evaluationService;

    @Autowired
    CourseRepository courseRepository;



    @RequestMapping("/home")
    public String evalHome(){
        return "/evalpages/evalhome";
    }

//
    @GetMapping("/evaluationentry/{courseid}")
    public String getEvaluation(@PathVariable("courseid") long id,  Model model) {

        Evaluation ev =  new Evaluation();
        ev.setCourseEvaluation(courseRepository.findOne(id));

        System.out.println("Course Id is"+ id);

        model.addAttribute("courseId",id);
        model.addAttribute("neweval", ev);

        return "evalpages/evaluationentry";

    }

    @PostMapping("/evaluationentry/{courseId}")
    public String entrypost(@Valid @ModelAttribute("neweval") Evaluation eval, @PathVariable("courseId")long courseId, BindingResult bindingResult,Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "evalpages/evaluationentry";
        }
        Course cr=courseRepository.findOne(courseId);
        evaluationService.addEvalToCourse(eval,cr);
        return "evalpages/confirmeval";
    }


    @GetMapping("/searchcourse")
    public String searchCourse()
    {

        return "evalpages/searchcourse";
    }

    @PostMapping("/searchcourse")
    public String searchCoursePost(@Valid @RequestParam("crnfield")long crn, Course course,BindingResult bindingResult,Model model )
    {
        if(bindingResult.hasErrors())
        {
            return "evalpages/evaluationentry";
        }
        model.addAttribute("searcheval", courseRepository.findAllByCrn(crn));


    //return "evalpages/searchresult";
       return "evalpages/searchcourse";
    }



}
