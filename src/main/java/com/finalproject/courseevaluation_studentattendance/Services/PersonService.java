package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Model.PersonRole;
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

    public Person findByUsername(String username){
        Person exPerson = personRepo.findByUsername(username);
        if(exPerson == null){
           throw new RuntimeException("username: " + username + " does not exist in personRepository");
        }
        else{
            return exPerson;
        }
    }

    public Person findById(long id){
        return personRepo.findOne(id);
    }

    public Iterable<Person> findByMNumber(String mNumber){
       return personRepo.findAllByMNumberLike("%"+mNumber+"%");
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


    //create a person that does not exist in the database.
    //assigns a default username if it is blank or null
    public Person create(Person aPerson) {
//        Person existingUser = personRepo.findByUsername(aPerson.getUsername());
//        if (existingUser != null) {
//            throw new RuntimeException("Record already exists!");
//        }
//        if(aPerson.getUsername()==null || aPerson.getUsername()==""){
//            Long count=personRepo.count()+1;
//            String fistNameFirstLetter=existingUser.getFirstName().substring(0);
//            String lastNameFirstLetter=existingUser.getLastName().substring(0);
//            String defaultUserName = String.format("%s%s%s",fistNameFirstLetter,lastNameFirstLetter,Long.toString(count));
//            aPerson.setUsername(defaultUserName);
//        }
        System.out.println("UserService: adding new user " + aPerson.toString());
        aPerson.addRole(roleRepo.findByRoleName("DEFAULT"));
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


    //check to see if a person has a role
    public Boolean personHasRole(Person aPerson, String aRoleName){
        Boolean output = false;

        Person ePerson = personRepo.findOne(aPerson.getId());
        if(ePerson == null){
            throw new RuntimeException("person does not exist in person repository");
        }
        PersonRole eRole = roleRepo.findByRoleName(aRoleName);
        if(eRole == null){
            throw new RuntimeException("Role " + aRoleName + "does not exist in role repository");
        }
        if(ePerson.getPersonRoles().contains(eRole)){
            output = true;
        }
        return output;
    }




}
