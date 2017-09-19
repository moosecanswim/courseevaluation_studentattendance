//package com.finalproject.courseevaluation_studentattendance.Repositories;
//
//import com.finalproject.courseevaluation_studentattendance.Model.Person;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.querydsl.QueryDslPredicateExecutor;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface PageAndSortingRepo extends PagingAndSortingRepository<Person, Long>
//{
//
//
//
//    @Query("select p from Person p where p.id = ?1")
//
//    Person findOne(Long id);
//
//}
