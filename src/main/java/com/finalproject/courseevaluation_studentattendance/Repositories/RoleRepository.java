package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.PersonRole;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<PersonRole,Long> {
}
