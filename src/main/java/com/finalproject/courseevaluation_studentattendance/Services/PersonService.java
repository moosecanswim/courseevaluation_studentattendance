package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class PersonService {
    @Autowired
    AttendanceRepository attendenceRepo;
    @Autowired
    CourseRepository courseRepo;
    @Autowired
    EvaluationRepository evaluationRepo;
    @Autowired
    PersonRepository personRepo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    PersonService(PersonRepository personRepository){
        this.personRepo=personRepository;
    }


    public Person findById(long id){
        return personRepo.findOne(id);
    }


    public void update(Person person){
        Person existingPerson = personRepo.findOne(person.getId());
        if(existingPerson==null){
            System.out.println("PersonService/update: input person not found in repository");
        }

        //start person id, username, and start date will not be changed when updated
        existingPerson.setmNumber(person.getmNumber());
        existingPerson.setFirstName(person.getFirstName());
        existingPerson.setLastName(person.getLastName());
        existingPerson.setEmail(person.getEmail());
        System.out.println("PersonService/update: sucessfully updated person details");
        personRepo.save(existingPerson);

    }

    public Person create(Person aPerson) {
        Person existingUser = personRepo.findByUsername(aPerson.getUsername());

        if (existingUser != null) {
            throw new RuntimeException("Record already exists!");
        }
        System.out.println("UserService: adding new user " + aPerson.toString());
        aPerson.addRole(roleRepo.findByRoleName("Student"));
        return personRepo.save(aPerson);
    }
    public void saveTeacher(Person aPerson){
        aPerson.addRole(roleRepo.findByRoleName("TEACHER"));
        aPerson.setActive(true);
        personRepo.save(aPerson);
    }
    public void saveAdmin(Person aPerson){
        aPerson.addRole(roleRepo.findByRoleName("ADMIN"));
        aPerson.setActive(true);
        personRepo.save(aPerson);
    }





}
