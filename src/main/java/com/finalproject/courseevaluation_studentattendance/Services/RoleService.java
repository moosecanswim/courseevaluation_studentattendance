package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.PersonRole;
import com.finalproject.courseevaluation_studentattendance.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
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
    public RoleService(RoleRepository roleRepository){
        this.roleRepo=roleRepository;
    }
    public void create(PersonRole role){
       PersonRole existingRole= roleRepo.findByRoleName(role.getRoleName());
        if(existingRole==null){
           System.out.println("RoleService:role does not exist.  It will be added");
           roleRepo.save(role);
        }
        else{
            System.out.println("RoleService:role existed so it was not added");
        }
    }

}
