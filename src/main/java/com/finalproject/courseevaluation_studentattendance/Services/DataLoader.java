package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Model.PersonRole;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    PersonRepository personRepo;

    @Override
    public void run(String... strings)throws Exception{
        System.out.println("Loading data . . .");


        roleRepo.save(new PersonRole("DEFAULT"));
        roleRepo.save(new PersonRole("TEACHER"));
        roleRepo.save(new PersonRole("ADMIN"));

        PersonRole defaultRole = roleRepo.findByRoleName("DEFAULT");
        PersonRole teacherRole = roleRepo.findByRoleName("TEACHER");
        PersonRole adminRole = roleRepo.findByRoleName("ADMIN");

        //make a person (first name, last name, username, password, email)
        Person aPerson= new Person("bob", "mcbobberson","bobbob", "password","bob@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(defaultRole);
        personRepo.save(aPerson);

        aPerson= new Person("matt", "mcmatterson","mattmatt", "password","matt@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(teacherRole);
        personRepo.save(aPerson);

        aPerson= new Person("tom", "O'tom","tomtom", "password","tom@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(teacherRole);
        personRepo.save(aPerson);

        aPerson= new Person("admin", "Mr.Admin","admin", "password","admin@email.com" );
        personRepo.save(aPerson);
        aPerson.addRole(teacherRole);
        aPerson.addRole(adminRole);
        personRepo.save(aPerson);
    }

}
