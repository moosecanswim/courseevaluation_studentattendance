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
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.InternetAddress;
import javax.websocket.server.PathParam;
import java.awt.*;
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
        model.addAttribute("searcheval", courseRepository.findAllByCrn(crn));


    //return "evalpages/searchresult";
       return "evalpages/searchcourse";
    }


    @RequestMapping("/evalslist")
    public String displayListOfEvaluatiosn(Model model, Evaluation evs)
    {
        model.addAttribute("ev", evaluationRepository.findAll());

        return "evalpages/evalslist";
    }



//
//    @Autowired
//    public EmailService emailService;
//
//    public void sendEmailWithoutTemplating() throws UnsupportedEncodingException {
//        final Email email = DefaultEmail.builder()
//                .from(new InternetAddress("tarig90@gmail.com.tarig", "Marco Tullio Cicerone "))
//                .to(Lists.newArrayList(new InternetAddress("titus@de-rerum.natura", "Pomponius Attĭcus")))
//                .subject("Laelius de amicitia")
//                .body("Firmamentum autem stabilitatis constantiaeque eius, quam in amicitia quaerimus, fides est.")
//                .body("")
//                .encoding("UTF-8").build();
//
//        emailService.send(email);
//    }
//
//
    @GetMapping("/evalsend/{id}")
    public String emailAtEvaluationForCourseEnd(@PathVariable("id") long id, Model model) throws UnsupportedEncodingException {
        Evaluation evaluation=evaluationRepository.findOne(id);
        Date date= new Date();
//        DateFormat df=new SimpleDateFormat("MM/dd/yyyy");
        //sets the course end date with the current date when they click here
        Course thiscourse = courseRepository.findOne(id);
        Iterable<Evaluation> thiseval = thiscourse.getEvaluations();
//
//        course.setEndDate(date);
//        courseRepository.save(course);
        System.out.println("test after save End date");
        attachmentContent(evaluation);
        return "redirect:/eval/evalslists/";

    }

    private String attachmentContent(Evaluation evaluation) throws UnsupportedEncodingException {

        String head="id,content,quality,experience,materials,environment,equipment,likes,dislikes,suggestions,otherClass,findings";

        //Iterable<Evaluation> evalsemail = evaluation.getCourseEvaluation();

       // Iterable<Evaluation> evals = evaluationRepository.findAllById(evaluation.getId());
        //Iterable<Course> students=course.getStudent();
//        System.out.println(course.getCourseName());
//        System.out.println("students in attachment method");



        //Iterable<Evaluation> thiscourseevaluation = evaluationRepository.findAllById(evaluation.getId());

        sendEmailWithoutTemplating(evaluation);

        return head;

    }

    @Autowired
    public EmailService emailService;
    public void sendEmailWithoutTemplating(Evaluation evaluation) throws UnsupportedEncodingException {
//        System.out.println("test before email");
//        System.out.println(course.getCourseName());
        final Email email= DefaultEmail.builder()
                .from(new InternetAddress("mymahder@gmail.com", "Attendance INFO"))
                .to(Lists.newArrayList(new InternetAddress("mymahder@gmail.com","admin")))
                .subject("Testing Email")
                .body("Course Closed.  Attendance for the class has been attached.")
                .attachment(getCsvForecastAttachment("Evaluation",evaluation))
                .encoding("UTF-8").build();
//		modelObject.put("recipent", recipent);
        System.out.println("test it");
        emailService.send(email);
        System.out.println(email.getBody());
    }
    private EmailAttachment getCsvForecastAttachment(String filename, Evaluation evaluation) {
        String testData="id,content,quality,experience,materials,environment,equipment,likes,dislikes,suggestions,otherClass,findings\n";
//        Iterable<> cr = evaluation.getCourseEvaluation();
     Iterable<Evaluation> evaluations = evaluationRepository.findAll();

        for (int i = 0; i < evaluationRepository.count(); i++)
        {
            String evaluationId = String.valueOf(evaluation.getId());
            String content = evaluation.getContent(); //std.getFirstName() +" "+ std.getLastName();
            String quality = evaluation.getQuality();
            String experience = evaluation.getExperience();
            String materials = evaluation.getMaterials();
            String environment = evaluation.getEnvironment();
            String equipment = evaluation.getEquipment();
            String likes = evaluation.getLikes();
            String dislikes = evaluation.getDislikes();
            String suggestions = evaluation.getSuggestions();
            String otherClass = evaluation.getOtherClass();
            String findings = evaluation.getFindings();
//            String studId = String.valueOf(std.getId());
//            String mnum = String.valueOf(std.getmNumber());
         //   Iterable<Attendance> attendances=std.getAttendances();
//            for (Attendance att: attendances) {
//                String dates=String.valueOf(att.getDate());
//                String status=att.getStatus();
//                testData += studId+","+ studName+","+mnum+","+dates+","+status+"\n";


        }

        DefaultEmailAttachment attachment = DefaultEmailAttachment.builder()
                .attachmentName(filename + ".csv")
                .attachmentData(testData.getBytes(Charset.forName("UTF-8")))
                .mediaType(MediaType.TEXT_PLAIN).build();

        return attachment;

    }




}
