package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Attendance;
import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.EvaluationService;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.EmailAttachment;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmailAttachment;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.InternetAddress;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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

//    @GetMapping("/sendevaluation/{id}")
//    public String emailEvaluation(@PathVariable("id") long id, Model model) throws UnsupportedEncodingException {
//        Course course=courseRepository.findOne(id);
//        Iterable<Evaluation>thiscrseval=course.getEvaluations();
//        System.out.println("test after save End date");
//        sendEmailWithoutTemplating(thiscrseval);
//        return "redirect:/admin/home";
//
//    }
//    @Autowired
//    public EmailService emailService;
//    public void sendEmailWithoutTemplating(Iterable<Evaluation>evaluations) throws UnsupportedEncodingException {
//        System.out.println("test before email");
//        for (Evaluation eval:evaluations) {
//            System.out.println(eval.getContent());
//            final Email email = DefaultEmail.builder()
//                    .from(new InternetAddress("mahifentaye@gmail.com", "Attendance INFO"))
//                    .to(Lists.newArrayList(new InternetAddress("mymahder@gmail.com", "admin")))
//                    .subject("Evaluation for" + eval.getCourseEvaluation())
//                    .body("Evaluation for this class has been attached.")
//                    .attachment(getCsvEvaluationAttachment("Attendance", evaluations))
//                    .encoding("UTF-8").build();
//            System.out.println("test it");
//            emailService.send(email);
//        }
//    }
//    private EmailAttachment getCsvEvaluationAttachment(String filename, Iterable<Evaluation>evaluations) {
//
//        String testData = "Course Content"+","+"Instruction Qyality"+","+"Training Experience"+","+"Textbooks or Handouts"+","+
//                "Environment and Seating"+","+"Computer Equipment"+","+"Likes"+","+"Dislikes"+","+"Suggegstions"+","+"Other classes"+","+
//                "\n";
//
//    for(Evaluation eval:evaluations) {
//        String content = eval.getContent();
//        String quality = eval.getQuality();
//        String experience = eval.getExperience();
//        String textbook = eval.getMaterials();
//        String environment = eval.getEnvironment();
//        String equipment = eval.getEquipment();
//        String likes = eval.getLikes();
//        String dislikes = eval.getDislikes();
//        String suggestion = eval.getSuggestions();
//        String otherclass = eval.getOtherClass();
//
//        testData += content + "," + quality + "," + experience + "," + textbook + "," + environment + "," + equipment
//                + "," + likes + "," + dislikes + "," + suggestion + "," + otherclass + "\n";
//    }
//        DefaultEmailAttachment attachment = DefaultEmailAttachment.builder()
//                .attachmentName(filename + ".csv")
//                .attachmentData(testData.getBytes(Charset.forName("UTF-8")))
//                .mediaType(MediaType.TEXT_PLAIN).build();
//
//        return attachment;
//    }


}
