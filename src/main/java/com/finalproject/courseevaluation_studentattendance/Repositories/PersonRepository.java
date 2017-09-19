package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person,Long> {

    Person findByUsername(String username);
   Iterable<Person>findAllByPersonRoles(String personrole);

   Iterable<Person> findAllByFirstNameLikeAndLastNameLikeAndEmailLike(String firstName, String lastName, String email);
    Iterable<Person> findAllByFirstNameLikeAndLastNameLike(String firstName, String lastName);
    Iterable<Person> findAllByFirstNameLike(String firstName);
    Iterable<Person> findAllByLastNameLike(String lastName);
    Iterable<Person> findAllByEmailLike(String firstName);

}
