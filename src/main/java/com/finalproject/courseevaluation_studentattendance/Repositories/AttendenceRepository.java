package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Attendence;
import org.springframework.data.repository.CrudRepository;

public interface AttendenceRepository extends CrudRepository<Attendence,Long> {
}
