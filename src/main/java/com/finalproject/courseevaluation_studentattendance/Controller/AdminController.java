package com.finalproject.courseevaluation_studentattendance.Controller;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Model.PersonRole;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.RoleRepository;
import com.finalproject.courseevaluation_studentattendance.Services.CommunicationService;
import com.finalproject.courseevaluation_studentattendance.Services.CourseService;
import com.finalproject.courseevaluation_studentattendance.Services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.finalproject.courseevaluation_studentattendance.Model.Communication;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController {
    //@Autowired
    //AttendanceService attendenceService;

    @Autowired
    PersonService personService;

    @Autowired
    CourseService courseService;

    @Autowired
    PersonRepository personRepo;

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    EvaluationRepository evaluationRepo;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CommunicationService communicationService;





    @RequestMapping("/home")
    public String adminHome(Model model){

        //Display all courses in the system regardless of status:
        //model.addAttribute("allcourses", courseRepo.findAll());

        //Display all courses in the system with status = true:
        model.addAttribute("allcourses", courseRepo.findAllByStatus(true));


        return "adminpages/adminhome";
    }

    @GetMapping("/addcourse")
    public String addCourse(Course newcourse, Model model)
    {
        model.addAttribute("newcourse",newcourse);

       Iterable<Course>courses=courseRepo.findAllById(newcourse.getId());
       for(Course c:courses)
       {
           System.out.println(c.getId());
       }

        long test=courses.spliterator().getExactSizeIfKnown();
        System.out.println(test);
       if(test==0)
       {
           return "adminpages/adminaddcourse";

       }

    return "adminpages/admineditcourse";

    }
    //
//
    //End date for the course isn't going to be entered here but it will be set when the teacher says the course ended
    @PostMapping("/addcourse")
    public String postCourse(@ModelAttribute("newcourse")Course newcourse)
    {
        courseRepo.save(newcourse);
        return"redirect:/admin/home";
    }
    //admin can register a student to a course here
    @GetMapping("/registerstudentforcourse/{id}")
    public String registerStudents(@PathVariable("id") long id, Model model)
    {

        Person student=new Person();
        Course ncourse=courseRepo.findOne(id);
        student.setCourseStudent(ncourse);
        model.addAttribute("newstudent", student);
        return "adminpages/adminregisterstudent";

    }

    @PostMapping("/registerstudent")
    public String saveStudent(@ModelAttribute("newstudent") Person newstudent)
    {

        personService.create(newstudent);
        return "redirect:/admin/home/";
    }
    @GetMapping("/updatecourse/{id}")
    public String editCourse(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("newcourse",courseRepo.findOne(id));

        return "adminpages/admineditcourse";
    }

    // ===   See the details of the Course
    @GetMapping("/admincoursedetails/{id}")  //id - course id
    public String displayCourse (@PathVariable("id")long id,
                                         Model model) {

        Course currentCourse = courseRepo.findOne(id);
        model.addAttribute("course", currentCourse);

        Person courseInstructor = currentCourse.getInstructor();
        model.addAttribute("courseInstructor", courseInstructor);

        Set<Person> courseStudents = currentCourse.getStudent();
        model.addAttribute("courseStudents", courseStudents);

        return "adminpages/admincoursedetails";
    }

    //need to taste viewing after the team is done with evaluation
    @GetMapping("viewcourseevaluations/{id}")
    public String viewEvaluation(@PathVariable("id") long id, Model model){
        Course thiscourse = courseRepo.findOne(id);
        Iterable<Evaluation> thiscourseevaluation = thiscourse.getEvaluations();
        model.addAttribute("evaluation",thiscourseevaluation);
        model.addAttribute("course",thiscourse);
        return"/adminpages/admincourseevaluation";
    }

    //this will allow the the admin to add an existing student to a course
    // the relationship between student and to courses is many to one so
    //it is not going to let them assign existing students to different courses
    // but if we change the relationship to allow that we can add them to a different
    // courses using this method
    @GetMapping("/addstudenttocourse/{id}")
    public String addStudent(@PathVariable("id") long crsID, Model model)
    {

      PersonRole nrole=roleRepository.findByRoleName("DEFAULT");
       Iterable<Person>students=nrole.getPeople();

        model.addAttribute("students",students);
        model.addAttribute("course",courseRepo.findOne(crsID));


        return "adminpages/admincourseaddstudent";
    }
    @PostMapping("/addstudenttocourse/{crsid}")
    public String studentSavedToCourse(@PathVariable("crsid") long id,
                                       @RequestParam("crs") String crsID,
                                       @ModelAttribute("aStudent") Person p,
                                       Model model)

    {
        Course ncourse=courseRepo.findOne(id);
        ncourse.addStudent(personRepo.findOne(new Long(crsID)));
        courseRepo.save(ncourse);
        return "redirect:/admin/admincoursedetails/"+crsID;
    }

    //this will allow the the admin to assign teachers to a course
    //since a course can only have one teacher every time the teachers chooses
    //different instructor it will reset it which is what we need
    @GetMapping("/addinstructortocourse/{id}")
    public String addTeacher(@PathVariable("id") long crsID, Model model)
    {

        PersonRole nrole=roleRepository.findByRoleName("TEACHER");
        Iterable<Person>teachers=nrole.getPeople();

        model.addAttribute("instructors",teachers);
        model.addAttribute("course",courseRepo.findOne(crsID));


        return "adminpages/admincourseaddinstructor";
    }
    @PostMapping("/addinstructortocourse/{crsid}")
    public String teacherSavedtoCourse(@PathVariable("crsid") long id,
                                       @RequestParam("crs") String crsID,
                                       @ModelAttribute("aInstructor") Person p,
                                       Model model)

    {
        Course ncourse=courseRepo.findOne(id);
        ncourse.setInstructor(personRepo.findOne(new Long(crsID)));
        courseRepo.save(ncourse);
        return "redirect:/admin/admincoursedetails/"+id;
    }



    // ===   Remove Course from the list of courses
    @RequestMapping("removecourse/{id}") //Course id
    public String removeCourse (@PathVariable("id")long id, Model model){

        Course course = courseRepo.findOne(id);
        System.out.println("course id to remove " + course.getId());
//        long courseToGoTo = course.getId();

        courseService.removeCourse(course);

        return "redirect:/admin/home"; //" + courseToGoTo;
    }

//    ToDo need to check how it works:
//    // ===   Remove Student from the Course
//    @RequestMapping("course/{courseid}/removestudentfromcourse/{studentid}")
    @RequestMapping("/removestudentfromcourse/{courseid}/{studentid}")
    public String removeStudentFromCourse (@PathVariable("courseid")long courseid,
                                           @PathVariable("studentid")long studentid,
                                           Model model)
    {
        Person student = personService.findById(studentid);

        System.out.println(student.getFirstName());

        Course course = courseRepo.findOne(courseid);
        System.out.println(course.getCourseName());
        courseService.removeStudentFromCourse(course, student);

        String courseIDString = Long.toString(courseid);
        return "rdirect:/admin/admincoursedatails/" + courseIDString;
    }






    // ===   See the List of All People
    @GetMapping("/viewallpeople")
    public String listallPeople(Person person, Model model)
    {
        model.addAttribute("person",personRepo.findAll());
        return"adminpages/adminviewallpeople";
    }


    // ===   See the List of All Teachers
    @GetMapping("/viewallteachers")
    public String seeAllTeachers(Model model)
    {
        PersonRole nrole=roleRepository.findByRoleName("TEACHER");
        Iterable<Person>teachers=nrole.getPeople();

        model.addAttribute("listOfAllTeachers", teachers);
        return "adminpages/adminviewallteachers";
    }


    // ===   See the List of All Students
    @GetMapping("/viewallstudents")
    public String seeAllStudents(Model model)
    {
        PersonRole nrole=roleRepository.findByRoleName("DEFAULT");
        Iterable<Person>students=nrole.getPeople();

        model.addAttribute("listOfAllStudents", students);
        return "adminpages/adminviewallstudents";
    }

    //admin edit the information of all people
    //this just takes back to the  registration form
    //which we need to change, updating person is going to be a little different after that
    @GetMapping("/updateperson/{id}")
    public String editPerson(@PathVariable("id") long id, Model model){
        model.addAttribute("person", personRepo.findOne(id));

        return "adminpages/admineditpeople";
    }


    @PostMapping("/updateperson")
    public String savePerson(@ModelAttribute("person") Person person)
    {

        personRepo.save(person);
        return "redirect:/admin/home/";
    }



    //COMMUNICATIONS methods

    @RequestMapping("/communicationhome")
    public String communicationHome(Model model){
        model.addAttribute("communicationListAvalible",communicationService.showByStatus(true));
        model.addAttribute("communicationListUnavalible",communicationService.showByStatus(false));
        return "/adminpages/admincommunicationhome";
    }

    @GetMapping("/addcommunication")
    public String newCommunication(Model model){
        Date now= new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String nowDate= df.format(now);

        model.addAttribute("now", now);
        model.addAttribute("aCommunication",new Communication());
        model.addAttribute("courseList",courseService.findAll());
        return "/adminpages/adminaddcommunication";
    }
    @PostMapping("/processcommunication/{type}")
    public String processCommunication(@Valid Communication aCom,@PathVariable("type") String type, BindingResult result){
        if(result.hasErrors()){
            System.out.println("Communication invalid- did not add");
            return "/adminpages/adminaddcommunication";
        }else{
            Date now= new Date();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String nowDate= df.format(now);

            switch (type){
                case "add":
                    //create a new communication
                    aCom.setCreatedOn(nowDate);
                    communicationService.create(aCom);
                    break;
                case "update":
                    //update communication
                    communicationService.update(aCom);
                    break;
            }

            return "redirect:/admin/communicationhome";
        }
    }

    @GetMapping("/updatecommunication/{id}")
    public String updateCommunication(@PathVariable("id") long communicationId,Model model){
        Date now= new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String nowDate= df.format(now);

        model.addAttribute("now", now);
        model.addAttribute("aCommunication",communicationService.findOne(communicationId));
        model.addAttribute("courseList",courseService.findAll());
        return "/adminpages/adminupdatecommunication";
    }
    @GetMapping("/togglecommunication/{id}")
    public String toggleCommunication(@PathVariable("id") long communicationId){
        communicationService.toggleCommunicationStatus(communicationService.findOne(communicationId));
        return "redirect:/admin/communicationhome";
    }

    @GetMapping("/searchcommunication")
    public String searchCommunication(@RequestParam("searchBy") String searchBy,@RequestParam("searchThing") String searchThing,Model model){

        switch (searchBy){
            case "all":
                //show all
                model.addAttribute("communicationListAvalible",communicationService.showByStatus(true));
                model.addAttribute("communicationListUnavalible",communicationService.showByStatus(false));
                break;
            case "courseCrn":
                //search by course crn (check to make sure search thing is an long input
                System.out.println("Search course crn");
                try{
                    Long crn=Long.valueOf(searchThing);
                    model.addAttribute("communicationListAvalible",communicationService.findByCrnAndStatus(crn,true));
                    model.addAttribute("communicationListUnvalible",communicationService.findByCrnAndStatus(crn,true));

                }catch(Exception e){
                    System.out.println("adminController- searchCommunication: search input not a long");
                    return "redirect:/admin/communicationhome";
                }
                     break;
            case "courseName":
                //search by course name (partial course names ok)
                System.out.println("Search course name");
                model.addAttribute("communicationListAvalible",communicationService.findByCourseNameAndStatus(searchThing,true));
                model.addAttribute("communicationListUnvalible",communicationService.findByCourseNameAndStatus(searchThing,false));
                break;
            case "phoneNumber":
                //search by phone number
                System.out.println("Search phoneNumber");
                model.addAttribute("communicationListAvalible",communicationService.findByPhoneNumberAndStatus(searchThing,true));
                model.addAttribute("communicationListUnvalible",communicationService.findByPhoneNumberAndStatus(searchThing,false));
                break;
            case "mNumber":
                //search by mNumber
                System.out.println("Search mNumber");
                model.addAttribute("communicationListAvalible",communicationService.findByMNumberAndStatus(searchThing,true));
                model.addAttribute("communicationListUnvalible",communicationService.findByMNumberAndStatus(searchThing,false));
                break;
            case "email":
                //search by email
                System.out.println("Search email");
                model.addAttribute("communicationListAvalible",communicationService.findByEmailAndStatus(searchThing,true));
                model.addAttribute("communicationListUnvalible",communicationService.findByEmailAndStatus(searchThing,false));
                break;
        }

        //returns to admin home if there is an error
        return "/adminpages/admincommunicationhome";
    }


    // admin create new person
//    @GetMapping("/addperson")
//    public String addPerson(Model model)
//    {
//        model.addAttribute("person", new Person());
//        return "adminpages/adminaddperson";
//    }
//
//    // Validate entered information and if it is valid display the result
//    // Person must have first name, last name, and email address
//    @PostMapping("/addperson")
//    public String postPerson(@ModelAttribute("person") Person person)
//    {
//        personRepo.save(person);
//        return"redirect:/admin/home";
//    }



}
