package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



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

//    @Autowired
//    EvaluationPagingService evaluationPagingService;


//    @RequestMapping(value="/persons",method=RequestMethod.GET)
//    Page<Evaluation> list( Pageable pageable){
//        Page<Evaluation> evals = .listAllByPage(pageable)
//        persons
//    }

    @RequestMapping("/home")
    public String evalHome() {
        return "/evalpages/evalhome";
    }

    //
    @GetMapping("/evaluationentry/{courseid}")
    public String getEvaluation(@PathVariable("courseid") long id, Model model) {

        Evaluation ev = new Evaluation();
        ev.setCourseEvaluation(courseRepository.findOne(id));


        model.addAttribute("courseId", id);
        model.addAttribute("neweval", ev);

        return "evalpages/evaluationentry";

    }

    @PostMapping("/evaluationentry/{courseId}")
    public String entrypost(@ModelAttribute("neweval") Evaluation eval, @PathVariable("courseId") long courseId, Model model) {
        Course cr = courseRepository.findOne(courseId);
        evaluationService.addEvalToCourse(eval, cr);
        return "evalpages/confirmeval";
    }


    @GetMapping("/searchcourse")
    public String searchCourse() {

        return "evalpages/searchcourse";
    }

    @PostMapping("/searchcourse")
    public String searchCoursePost(@RequestParam("crnfield") long crn, Model model, Course course) {
        model.addAttribute("searcheval", courseRepository.findAllByCrn(crn));


        //return "evalpages/searchresult";
        return "evalpages/searchcourse";
    }


//    @RequestMapping("/evalslist")
//    public String displayListOfEvaluatiosn(Model model, Evaluation evs)
//    {
//        model.addAttribute("ev", evaluationRepository.findAll());
//
//        return "evalpages/evalslist";
//    }


//    @RequestMapping("/evalslist")
//    public showUsers(Pageable pageable) {
//        Page<Evaluation> allBy = evaluationRepository.findAllBy(pageable);
//        return allBy;
//
//
//    }
//
//    @RequestMapping("/evalslist")
//    Page<Evaluation> list(Pageable pageable) {
//        Page<Evaluation> evaluations = evaluationRepository.findAllBy(pageable);
//
//        System.out.println(evaluations);
//        return evaluations;
//
//    }
//
//    public String showUsers(Model model, @Qualifier("foo") Pageable first,
//                            @Qualifier("bar") Pageable second)
//


    @RequestMapping("/evalslist")
    public String showUsers(Model model, Pageable pageable)
    {

       model.addAttribute("evals", evaluationRepository.findAll(pageable));
      // System.out.println(pageable);

       return "evalpages/evalslist";

    }

//    @RequestMapping("/evalslist")
//    public Page<Evaluation> showUsers(Pageable pageable) {
//        return evaluationRepository.findAll(pageable);
//    }



}
