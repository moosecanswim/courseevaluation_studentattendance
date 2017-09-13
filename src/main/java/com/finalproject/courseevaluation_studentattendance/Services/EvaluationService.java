package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Repositories.AttendenceRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.CourseRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {
    @Autowired
    AttendenceRepository attendenceRepo;
    @Autowired
    CourseRepository courseRepo;
    @Autowired
    EvaluationRepository evaluationRepo;
    @Autowired
    PersonService personRepo;
    @Autowired
    RoleRepository roleRepo;
}
