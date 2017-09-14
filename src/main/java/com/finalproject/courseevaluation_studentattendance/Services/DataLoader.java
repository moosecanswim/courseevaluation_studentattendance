package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Model.PersonRole;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    PersonRepository personRepo;
    @Autowired
    CourseRepository courseRepo;

    @Override
    public void run(String... strings)throws Exception{
        System.out.println("Loading data . . .");


        ///////////////////Create Roles////////////////////////
        roleRepo.save(new PersonRole("DEFAULT"));
        roleRepo.save(new PersonRole("TEACHER"));
        roleRepo.save(new PersonRole("ADMIN"));

        PersonRole defaultRole = roleRepo.findByRoleName("DEFAULT");
        PersonRole teacherRole = roleRepo.findByRoleName("TEACHER");
        PersonRole adminRole = roleRepo.findByRoleName("ADMIN");



        ////////////////Create people with the roles/////////////////

        //make a person ([String] first name,[String] last name,[String] username,[String] password,[String] email)
        Person aPerson= new Person("bob", "mcbobberson","bobbob", "password","bob@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(defaultRole);
        personRepo.save(aPerson);


        //load teacher
        aPerson= new Person("matt", "mcmatterson","mattmatt", "password","matt@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(teacherRole);
        personRepo.save(aPerson);

        //load teacher
        aPerson= new Person("tom", "O'tom","tomtom", "password","tom@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(teacherRole);
        personRepo.save(aPerson);

        //load teacher
        aPerson= new Person("teacher", "Mr.teacher","teacher", "password","teacher@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(teacherRole);
        personRepo.save(aPerson);

        //
        aPerson= new Person("admin", "Mr.Admin","admin", "password","admin@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(teacherRole);
        aPerson.addRole(adminRole);
        personRepo.save(aPerson);

        //empty username to see if default will work
        aPerson= new Person("student", "name","", "password","noname@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(teacherRole);
        aPerson.addRole(adminRole);
        personRepo.save(aPerson);


        ///////////////////////Create Courses/////////////////////
        //make a course with a ([long] crn, [String] course name, [date] start date, [date] end date

        Calendar cal1 = Calendar.getInstance();
        cal1.set(2013, Calendar.JANUARY, 9); //Year, month and day of month
        Date startDate = cal1.getTime();
        Calendar cal2 = Calendar.getInstance();
        cal2.set(2013, Calendar.JANUARY, 9); //Year, month and day of month
        Date endDate = cal2.getTime();

        Course course = new Course(123,"Math",startDate,endDate);
        courseRepo.save(course);
        course.addStudent(personRepo.findByUsername("bobbob"));
        course.addStudent(personRepo.findByUsername("student"));
        course.setInstructor(personRepo.findByUsername("teacher"));
        courseRepo.save(course);

        course = new Course(456,"Physics",startDate,endDate);
        courseRepo.save(course);
        course.setInstructor(personRepo.findByUsername("teacher"));

        course = new Course(789,"German",startDate,endDate);
        courseRepo.save(course);
        course.setInstructor(personRepo.findByUsername("teacher"));
        courseRepo.save(course);

        course = new Course(101,"Java",startDate,endDate);
        courseRepo.save(course);

        //add course to teacher





    }



}
