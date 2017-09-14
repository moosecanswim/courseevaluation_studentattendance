package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import com.finalproject.courseevaluation_studentattendance.Services.AttendanceService;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {
    //@Autowired
    //AttendanceService attendenceService;

//    @Autowired
//    PersonService personService;

    @Autowired
    PersonRepository personRepo;

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    EvaluationRepository evaluationRepo;






    @RequestMapping("/home")
    public String adminHome(Model model){


        model.addAttribute("allcourses", courseRepo.findAll());
        return "adminpages/adminhome";
    }

    @GetMapping("/addcourse")
    public String addCourse(Model model)
    {
        model.addAttribute("newcourse", new Course());
        return "adminpages/addcourse";
    }

    //End date for the course isn't going to be entered here but it will be set when the teacher says the course ended
    @PostMapping("/addcourse")
    public String postCourse(@ModelAttribute("newcourse")Course newcourse)
    {
        courseRepo.save(newcourse);

         return"redirect:/admin/addcourse";
    }
    @GetMapping("/updatecourse/{id}")
    public String editCourse(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newcourse",courseRepo.findOne(id));

        return "adminpages/addcourse";
    }

    @GetMapping("/detailsofacourse/{id}")
    public String displayCourse (@PathVariable("id")long id,
                                         Model model) {

        Course currentCourse = courseRepo.findOne(id);
        model.addAttribute("course", currentCourse);

        Set<Person> courseInstructors = currentCourse.getInstructor();
        model.addAttribute("courseInstructors", courseInstructors);

        Set<Evaluation> courseEvaluations = currentCourse.getEvaluations();
        model.addAttribute("courseEvaluations", courseEvaluations);

        Set<Person> courseStudents = currentCourse.getStudent();
        model.addAttribute("courseStudents", courseStudents);

        return "adminpages/datailsofacourse";
    }




}
