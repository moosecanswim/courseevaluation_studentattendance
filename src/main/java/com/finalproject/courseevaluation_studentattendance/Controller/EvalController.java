package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/eval")
public class EvalController {
    @Autowired
    EvaluationRepository evaluationRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;



    @RequestMapping("/home")
    public String evalHome(){
        return "/evalpages/evalhome";
    }

    @GetMapping("/evaluation/{id}")
    public String getEvaluation(@PathParam("id")Long id, Model model)
    {

        Course course = courseRepository.findOne(id);

        Evaluation evalforaCourse = new Evaluation();

        evalforaCourse.setCourseEvaluation(course);

        model.addAttribute("course", course);

        model.addAttribute("neweval", evalforaCourse);

        return "evalpages/evaluation";
    }

    @RequestMapping("/evaluation/{id}")
    public String postEvaluation(@PathParam("id")Long id,Evaluation evaluation, Model model)
    {

        String testcrn = null;

        courseService.findByCRN(testcrn);
        model.addAttribute("neweval", evaluation);
        evaluationRepository.save(evaluation);
        return "evaluation";
    }

}
