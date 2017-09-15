package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.*;
import com.finalproject.courseevaluation_studentattendance.Repositories.*;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.internet.InternetAddress;
import javax.websocket.server.PathParam;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;


import javax.websocket.server.PathParam;

@Controller
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    PersonRepository personRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    PersonRepository personRepo;

    @Autowired
    PersonService personService;

    @Autowired
    CourseService courseService;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    EvaluationRepository evaluationRepository;

    @RequestMapping("/home")
//    public String teacherHome(Principal p, Model model)
//    {
//        model.addAttribute("instructor", personRepository.findByUsername(p.getName()));
//        return "teacherpages/teacherhome";
//    }

    //just for testing until security/login option is added
    public String teacherHometest(Model model)
    {
        model.addAttribute("instructor", personRepository.findByUsername("teacher"));
        return "teacherpages/teacherhome";
    }

    //this route can be combine with the teacherhome page later
    @GetMapping("/listallcourses")
//    public String listCourse(Principal p, Model model)
    public String listCourse(Model model)
    {
        Person instructor = personRepository.findByUsername("teacher");

        Iterable<Course> allCoursesofAInstructor = instructor.getCourseInstructor();

        model.addAttribute("allcoursesofaInstructor", allCoursesofAInstructor);

        return "teacherpages/listallcourses";
    }


    //list course info and all students/mark attendance
    @GetMapping("/detailsofacourse/{id}")
    public String detailsofcourse(@PathVariable("id") Long courseId, Model model)
    {

        Course currentCourse = courseRepository.findOne(courseId);
        Iterable<Person> studentsofACourse = currentCourse.getStudent();


        //move it to new route so it can stamp the time of the time actually submitted
        //add a new attendance and set date (for a course)
//       Date now= new Date();

//        Attendance oneAttendancecourse = new Attendance();
//        Attendance oneAttendance = new Attendance();
//
//        oneAttendancecourse.setDate(now);
//        oneAttendance.setDate(now);

//        currentCourse.addAttendance(oneAttendancecourse);
//
//                for (Person student : studentsofACourse) {
//            student.addAttendance(oneAttendance);
//        }


        model.addAttribute("course", currentCourse);
        model.addAttribute("studentsofACourse", studentsofACourse);



        return "teacherpages/detailsofacourse";
    }


    @GetMapping("/markattendance/{courseId}")
    public String listAttendanceofaCourse(@PathVariable("courseId") Long courseId, Model model)
    {


        Course currentCourse = courseRepository.findOne(courseId);
        Iterable<Person> studentsofACourse = currentCourse.getStudent();

        Date now= new Date();



        model.addAttribute("now", now);
        model.addAttribute("course", currentCourse);
        model.addAttribute("studentsofACourse", studentsofACourse);

        return "teacherpages/attendanceofacourseform";
    }

    //display the attendance for a course of all students



    @PostMapping("/markattendance/{courseId}")
    public String postattendance(@PathVariable("courseId") Long courseId, @RequestParam(value = "attendanceStatus") String[] attendanceStatus, Model model)
    {
        Course currentCourse = courseRepository.findOne(courseId);
        Iterable<Person> studentsofACourse = currentCourse.getStudent();

        int i=0;

        Date now= new Date();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        String nowdate= df.format(now);

        for (Person student: studentsofACourse)
        {

            // TODO: update student attendance status (for the same day same course)
            // make the following comment-out method work to prevent Att status being set twice for the same student for the same day
            //if this same's attendance status has been set, only update the attendance status, rather than create a new attendance

//            try {
//
//            for (Attendance att : student.getAttendances())
//            {
//
//                if (att.getDate()== nowdate && att.getAttendanceCourse()==currentCourse)
//                {
////                       !!!maybe an update method here would work
//                    att.setStatus(attendanceStatus[i]);
//                    //attendanceRepository.save(att);
//                    i+=1;
//                    return "teacherpages/displyattforstudentsofacourse";
//                }
//
//            } } catch (Exception e) {
//                System.out.println("this is the first time marking attendance for the student today");
//
//            }

             Attendance att = new Attendance();

//            df.format(now) returns a string
                att.setDate(nowdate);
//            att.setDate(now);
                att.setStatus(attendanceStatus[i]);
                i += 1;
                att.setPersonAttendance(student);
                att.setAttendanceCourse(currentCourse);
                attendanceRepository.save(att);


        }


        model.addAttribute("now", now);
        model.addAttribute("course", currentCourse);
        model.addAttribute("studentsofACourse", studentsofACourse);


        return "teacherpages/displyattforstudentsofacourse";
    }


   @GetMapping("/addstudentstocourse/{id}")
   public String getCourse(@PathVariable("id")Long id, Model model)
   {

       Date now= new Date();

       Person student = new Person();

       student.setStartDate(now);

       System.out.println(student.getStartDate());

       model.addAttribute("course", courseRepository.findOne(id));

       model.addAttribute("newstudent", student);

       return "teacherpages/addstudent";
   }


   @PostMapping("/addstudent/{id}")
    public String postCourse(@PathVariable("id") Long id, @ModelAttribute("newstudent") Person student, Model model)
   {

       Course c =  courseRepository.findOne(id);
       student.setCourseStudent(c);
       personRepository.save(student);
//       personService.addStudentToCourse(student,c);
       model.addAttribute("newstudent", student);
//       personService.create(student);
//      // personRepo.save(person);
       return "teacherpages/confirmstudent";
   }



   //why we need this method? T
   @RequestMapping("/displaystudents")
    public String displayStudents(@ModelAttribute("lstudents")Person person, Model model)
   {
        model.addAttribute("lstudents", personRepo.findAll());
        return "teacherpages/displaystudents";
   }

//
//   @RequestMapping("searchcrn")
//    public String searchForCRN(@ModelAttribute("crn") Evaluation eval, Model model, Course cse)
//   {
//
//   }
   @GetMapping("/evaluation/{id}")
    public String getEvaluation(@PathVariable("id")Long id, Model model)
   {
       model.addAttribute("neweval", new Evaluation());
    return "teacherpages/evaluation";
   }
   @PostMapping("/evaluation/{id}")
   public String postEvaluation(@PathVariable("id")Long id,Evaluation evaluation, Model model)
   {
       model.addAttribute("neweval", evaluation);
       evaluationRepository.save(evaluation);
       return "teacherpages/evaluation";
   }

//the method to send email
    //it sends email need to make the body

    @Autowired
    public EmailService emailService;
    public void sendEmailWithoutTemplating() throws UnsupportedEncodingException {
        final Email email= DefaultEmail.builder()
                .from(new InternetAddress("mahifentaye@gmail.com", "Marco Tullio Cicero ne"))
                .to(Lists.newArrayList(new InternetAddress("mymahder@gmail.com","admin")))
                .subject("Testing Email")
                .body("We need the attendance in the Email body.")
                .encoding("UTF-8").build();
//		modelObject.put("recipent", recipent);
        System.out.println("test it");
        emailService.send(email);
    }
    @GetMapping("/courseend")
    public String emailAtCourseEnd(Model model) throws UnsupportedEncodingException {
        Date now= new Date();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        String nowdate= df.format(now);
        //we need to set the course end date with the current date when they click here
        sendEmailWithoutTemplating();
        return"redirect:/teacher/displaystudents/";
    }

}
