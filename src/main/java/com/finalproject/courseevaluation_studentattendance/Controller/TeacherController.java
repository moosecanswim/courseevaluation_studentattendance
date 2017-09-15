package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.*;
import com.finalproject.courseevaluation_studentattendance.Repositories.*;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.Date;


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
    public String postattendance(@PathVariable("courseId") Long courseId, @RequestParam(value = "attendanceStatus", required = false) String[] attendanceStatus, Model model)
    {
        Course currentCourse = courseRepository.findOne(courseId);
        Iterable<Person> studentsofACourse = currentCourse.getStudent();

        int i=0;

        Date now= new Date();

        for (Person student: studentsofACourse)
        {

            Attendance att = new Attendance();
            att.setDate(now);
            att.setStatus(attendanceStatus[i]);
            i+=1;
            att.setPersonAttendance(student);
            att.setAttendanceCourse(currentCourse);
            attendanceRepository.save(att);

        }



        model.addAttribute("now", now);
        model.addAttribute("course", currentCourse);
        model.addAttribute("studentsofACourse", studentsofACourse);


        return "teacherpages/attendanceforstudentsofacourse";
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
       student.addCourse(c);
       personRepository.save(student);
//       personService.addStudentToCourse(student,c);
       model.addAttribute("newstudent", student);
//       personService.create(student);
//      // personRepo.save(person);
       return "teacherpages/confirmstudent";
   }

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


}
