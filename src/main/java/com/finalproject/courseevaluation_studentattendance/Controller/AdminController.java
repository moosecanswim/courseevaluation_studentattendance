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
import com.finalproject.courseevaluation_studentattendance.Model.Communication;

import javax.mail.internet.InternetAddress;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
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
    @Autowired
    public EmailService emailService;




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
    public String postCourse(@Valid @ModelAttribute("newcourse")Course newcourse, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return"adminpages/adminaddcourse";
        }
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
    public String saveStudent(@Valid @ModelAttribute("newstudent") Person newstudent, BindingResult bindingResult)
    {
     if(bindingResult.hasErrors())
     {
         return "adminpages/adminregisterstudent";
     }
        personService.create(newstudent);
        return "redirect:/admin/home/";
    }
    @RequestMapping("/admincourseevaluation")
    public String displayAll(Model model)
    {
        model.addAttribute("allevals", evaluationRepo.findAll());

        return "adminpages/admincourseevaluation";
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
    @GetMapping("viewcourseevaluations/{id}")  // course id
    public String viewEvaluation(@PathVariable("id") long id, Model model){
        Course thiscourse = courseRepo.findOne(id);
        Iterable<Evaluation> thiscourseevaluation = thiscourse.getEvaluations();
        Person courseInstructor = thiscourse.getInstructor();

        model.addAttribute("evaluation",thiscourseevaluation);
        model.addAttribute("course",thiscourse);
        model.addAttribute("courseInstructor",courseInstructor);
        return"/adminpages/admincourseevaluation";
    }

    @GetMapping("admineval/{id}")  // course id
    public String adminviewEvaluation(@PathVariable("id") long id, Model model){
        Course thiscourse = courseRepo.findOne(id);

       Iterable<Evaluation> thiscourseevaluation = thiscourse.getEvaluations();

//        Person courseInstructor = thiscourse.getInstructor();

        model.addAttribute("evals",thiscourseevaluation);
//        model.addAttribute("course",thiscourse);
//        model.addAttribute("courseInstructor",courseInstructor);
        return"/adminpages/admineval";
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


//    // ===   Remove Student from the Course:
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
        return "redirect:/admin/admincoursedetails/" + courseIDString;
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



    /*
    links-from admin/viewallstudents
    admin/studentinformation/{mNumber} takes in an mNumber and finds an Iterable of all
    profiles tied to that mNumber.  It then goes through each course and checks to see if
    student profile is assigned as a student to that course.  when it finds a course
    that the student's profile is in it checks to see if the course is avalible or has ended
    if it has ended then it will add to studentCoursesAvalible otherwise it will add the course to
    studentCourseUnavalible
     */
    @GetMapping("/studentinformation/{id}")
    public String studentInformation(@PathVariable("id")Long id,Model model){
        Person aStudent=personService.findById(id);
        Iterable<Person> studentProfiles = personService.findByMNumber(aStudent.getmNumber());
        Set<Course> studentCoursesAvalible = new HashSet<Course>();
        Set<Course> studentCoursesUnvalible = new HashSet<Course>();

        for(Course c : courseService.findAll()){
           for(Person s:studentProfiles){
               if(c.getStudent().contains(s)){
                  if(c.getStatus()){
                      studentCoursesAvalible.add(c);
                  }
                  else{
                      studentCoursesUnvalible.add(c);
                  }
               }
           }

        }
        model.addAttribute("aStudent",aStudent);
        model.addAttribute("allCoursesEnrolled", studentCoursesAvalible);
        model.addAttribute("allCoursesEnded",studentCoursesUnvalible);
    return "adminpages/adminstudentdetails";
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
    public String savePerson(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult)
    {
       if(bindingResult.hasErrors())
       {
           return"adminpages/admineditpeople";
       }
        personRepo.save(person);
        return "redirect:/admin/home/";
    }


    // ===   See the List of this Teacher Courses
    @GetMapping("/viewteachercourses/{id}") // teacher id
    public String seeAllTeachers(@PathVariable("id") long id, Model model)
    {

        Person teacher = personService.findById(id);
        model.addAttribute("teacher", teacher);

        Iterable<Course> courses=teacher.getCourseInstructor();

        model.addAttribute("listAllCoursesForThisTeacher", courses);
        return "adminpages/adminviewoneteachercourses";
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
            case"name":
                //search by name
                System.out.println("adminController- searchCommunication: search by contact name");
                model.addAttribute("communicationListAvalible",communicationService.findByNameAndAvalible(searchThing));
                model.addAttribute("communicationListUnavalible",communicationService.findByNameAndUnavalible(searchThing));
                break;
            case "courseCrn":
                //search by course crn (check to make sure search thing is an long input
                System.out.println("Search course crn");

                model.addAttribute("communicationListAvalible",communicationService.findByCrnAndStatus(searchThing,true));
                model.addAttribute("communicationListUnvalible",communicationService.findByCrnAndStatus(searchThing,false));

                break;
//            case "courseName":
//                //search by course name (partial course names ok)
//                System.out.println("Search course name");
//                model.addAttribute("communicationListAvalible",communicationService.findByCrnAndStatus(searchThing,true));
//                model.addAttribute("communicationListUnvalible",communicationService.findByCrnAndStatus(searchThing,false));
//                break;
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


//     admin create new Student
    @GetMapping("/addperson")
    public String addPerson(Model model)
    {
        model.addAttribute("person", new Person());
        return "adminpages/adminaddperson";
    }

    // Validate entered information and if it is valid display the result
    // Person must have first name, last name, and email address
    @PostMapping("/addperson")
    public String postPerson(@Valid @ModelAttribute("person") Person person, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return "adminpages/adminaddperson";
        }

        personService.create(person);
        return"redirect:/admin/home";
    }



    @GetMapping("/sendevaluation/{id}")
    public String emailEvaluation(@PathVariable("id") long id, Model model) throws UnsupportedEncodingException {
        Course course=courseRepo.findOne(id);
        Iterable<Evaluation>thiscrseval=course.getEvaluations();
        System.out.println("test after save End date");
        sendEmailWithoutTemplating(thiscrseval);
        return "redirect:/teacher/listallcourses/";

    }

    public void sendEmailWithoutTemplating(Iterable<Evaluation>evaluations) throws UnsupportedEncodingException {
        System.out.println("test before email");
        Evaluation eval=new Evaluation();
        Person admin=new Person();
        System.out.println();
        for (Evaluation neval:evaluations) {
            eval=neval;
            System.out.println("Courssssssssssssss"+eval.getCourseEvaluation().toString());
        }
        PersonRole nrole=roleRepository.findByRoleName("ADMIN");
        Iterable<Person>persons=nrole.getPeople();
        for (Person np:persons)
        {
            admin=np;
            System.out.println("Admin name-----------"+np.getFirstName()+np.getLastName());
            System.out.println("Admin Roles"+np.getPersonRoles().toString());
        }
        String adminemail=String.valueOf(admin.getEmail());
        System.out.println("emailllllllllllll"+adminemail);
            System.out.println(eval.getContent());
            final Email email = DefaultEmail.builder()
                    .from(new InternetAddress("mahifentaye@gmail.com", "Evaluation INFO"))
                    .to(Lists.newArrayList(new InternetAddress(adminemail, "admin")))
                    .subject("Evaluation for" + eval.getCourseEvaluation())
                    .body("Evaluation for "+eval.getCourseEvaluation()+ " has been attached.")
                    .attachment(getCsvEvaluationAttachment("Evaluation", evaluations))
                    .encoding("UTF-8").build();
            System.out.println("test it");
            emailService.send(email);

    }
    private EmailAttachment getCsvEvaluationAttachment(String filename, Iterable<Evaluation>evaluations) {

        String testData = "Course Content"+","+"Instruction Quality"+","+"Training Experience"+","+"Textbooks or Handouts"+","+
                "Environment and Seating"+","+"Computer Equipment"+","+"Likes"+","+"Dislikes"+","+"Suggegstions"+","+"Other classes"+","+
                "How did you find about this class"+"\n";

        for(Evaluation eval:evaluations) {
            String content = eval.getContent();
            String quality = eval.getQuality();
            String experience = eval.getExperience();
            String textbook = eval.getMaterials();
            String environment = eval.getEnvironment();
            String equipment = eval.getEquipment();
            String likes = eval.getLikes();
            String dislikes = eval.getDislikes();
            String suggestion = eval.getSuggestions();
            String otherclass = eval.getOtherClass();
            String hearaboutclass=eval.getFindings();

            testData += content + "," + quality + "," + experience + "," + textbook + "," + environment + "," + equipment
                    + "," + likes + "," + dislikes + "," + suggestion + "," + otherclass + ","+hearaboutclass+"\n";
        }
        DefaultEmailAttachment attachment = DefaultEmailAttachment.builder()
                .attachmentName(filename + ".csv")
                .attachmentData(testData.getBytes(Charset.forName("UTF-8")))
                .mediaType(MediaType.TEXT_PLAIN).build();

        return attachment;
    }


}
