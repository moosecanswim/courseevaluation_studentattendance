package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.*;
import com.finalproject.courseevaluation_studentattendance.Repositories.*;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import com.google.common.collect.Lists;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.EmailAttachment;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmailAttachment;
import it.ozimov.springboot.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.internet.InternetAddress;
import javax.websocket.server.PathParam;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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



    @PostMapping("/markattendancepo/{courseId}")
    public String postattendance(@PathVariable("courseId") Long courseId, @RequestParam(value = "attendanceStatus") String[] attendanceStatus, Model model)
    {
        Course currentCourse = courseRepository.findOne(courseId);
        Iterable<Person> studentsofACourse = currentCourse.getStudent();

        int i=0;

        Date now= new Date();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        String nowdate= df.format(now);

        System.out.println(nowdate);
//
        for (Person student: studentsofACourse) {

            if (attendanceRepository.findAllByAttendanceCourseEqualsAndDateEqualsAndPersonAttendanceEquals(currentCourse, nowdate, student) != null) {
                Attendance attdel = attendanceRepository.findAllByAttendanceCourseEqualsAndDateEqualsAndPersonAttendanceEquals(currentCourse, nowdate, student);
                System.out.println(attdel.toString());
                student.removeAttendance(attdel);
                System.out.println("here1111111");
                attendanceRepository.delete(attdel);
//                System.out.println("here2222211");
            }

                Attendance attnew = new Attendance();
                attnew.setDate(nowdate);
                System.out.println("printing status" + attendanceStatus[i]);
                attnew.setStatus(attendanceStatus[i]);
                System.out.println("set stautus doone----");
                i += 1;
                attnew.setPersonAttendance(student);
                student.addAttendance(attnew);
                System.out.println("!!!!!!-----add att to student");
                attnew.setAttendanceCourse(currentCourse);
                attendanceRepository.save(attnew);

                System.out.println("newset-------");

                // problem is here is empty
                System.out.println("!!!!!!!!"+student.getAttendances().toString());

        }


        System.out.println("end loop------");

        model.addAttribute("now", now);
        model.addAttribute("course", currentCourse);
        model.addAttribute("studentsofACourse", studentsofACourse);


        return "teacherpages/displyattforstudentsofacourse";
    }

    //for delete or update M number for the student
    @GetMapping("/listallstudents/{courseId}")
    public String updateMnumber(@PathVariable("courseId") Long courseId, Model model) {


        Course currentCourse = courseRepository.findOne(courseId);
        Iterable<Person> studentsofACourse = currentCourse.getStudent();
        ArrayList<Person> unvalidatedstudent= new ArrayList<>();
        ArrayList<Person> validatedstudent= new ArrayList<>();

        //for student that M number is null put them in a unvalidated list
        for (Person student:studentsofACourse)
        {
            if(student.getmNumber().isEmpty())
            {
                unvalidatedstudent.add(student);
            }

            if(student.getmNumber()!=null)
            {
                System.out.println("not null====" + student.getmNumber().toString());
                validatedstudent.add(student);
            }

        }

        model.addAttribute("unvalidatedstudent", unvalidatedstudent);
        model.addAttribute("validatedstudent", validatedstudent);

        return "teacherpages/updatemnum";



    }

    @PostMapping("/mforallstudents/{courseId}")
    public String updateMnumberordeletestudent(@PathVariable("courseId") Long courseId, Model model) {

        Course currentCourse = courseRepository.findOne(courseId);
        Iterable<Person> studentsofACourse = currentCourse.getStudent();
        model.addAttribute("allstudent", studentsofACourse);

        return "redirect:/teacherpages/listallstudents/{courseId}";

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
       model.addAttribute("course", c);
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

    private EmailAttachment getCsvForecastAttachment(String filename) {
        final String testData =
                "years from now,death probability\n1,0.9\n2,0.95\n3,1.0";
        final DefaultEmailAttachment attachment = DefaultEmailAttachment.builder()
                .attachmentName(filename + ".csv")
                .attachmentData(testData.getBytes(Charset.forName("UTF-8")))
                .mediaType(MediaType.TEXT_PLAIN).build();
        return attachment;
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
