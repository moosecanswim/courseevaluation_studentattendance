package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person,Long> {
}
