package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person,Long> {

    Person findByUsername(String username);
   Iterable<Person>findAllByPersonRoles(String personrole);

   Iterable<Person> findByFirstNameLikeAndLastNameLikeAndEmailLike(String firstName, String lastName, String email);
    Iterable<Person> findByFirstNameLikeAndLastNameLike(String firstName, String lastName);
    Iterable<Person> findByFirstNameLike(String firstName);
    Iterable<Person> findByLastNameLike(String lastName);
    Iterable<Person> findByEmailLike(String firstName);


}
