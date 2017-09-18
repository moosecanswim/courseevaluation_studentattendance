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
import org.springframework.web.bind.annotation.*;

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
    public String getEvaluation(@PathVariable("courseid") long id,  Model model, Course cr) {

        Evaluation ev =  new Evaluation();
        //ev.setCourseEvaluation(courseRepository.findOne(id));

        System.out.println("Course Id is"+ id);
        model.addAttribute("courseId",id);
        model.addAttribute("neweval", ev);

        return "evalpages/evaluationentry";


    }

    @PostMapping("/evaluationentry/{courseId}")
    public String entrypost(@ModelAttribute("neweval") Evaluation eval,@PathVariable("courseId")long courseId, Model model)
    {
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
    public String searchCoursePost(@RequestParam("crnfield")long crn, Model model,Course course )
    {
        model.addAttribute("searcheval", courseRepository.findByCrn(crn));
        return "evalpages/searchresult";
    }




    // add evaluation to course







}
