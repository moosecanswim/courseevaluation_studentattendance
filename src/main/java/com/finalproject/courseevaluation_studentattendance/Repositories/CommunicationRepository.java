package com.finalproject.courseevaluation_studentattendance.Repositories;

import com.finalproject.courseevaluation_studentattendance.Model.Communication;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CommunicationRepository extends CrudRepository<Communication,Long> {
    //status
    Set<Communication> findAllByCallStatus(Boolean callStatus);
    Long countAllByCallStatus(Boolean callStatus);

    //M#
    Set<Communication> findByMNumber(String mNumber);
    Long countByMNumber(String mNumber);
    Set<Communication> findByMNumberAndCallStatus(String mNumber, Boolean callStatus);
    Long countByMNumberAndCallStatus(String mNumber, Boolean callStatus);

    //Email
    Set<Communication> findByEmail(String email);
    Long countByEmail(String email);
    Set<Communication> findByEmailAndCallStatus(String email, Boolean callStatus);
    Long countByEmailAndCallStatus(String email, Boolean callStatus);


    //phoneNumber
    Set<Communication> findByPhoneNumber(String phoneNumber);
    Long countByPhoneNumber(String phoneNumber);
    Set<Communication> findByPhoneNumberAndCallStatus(String phoneNumber, Boolean callStatus);
    Long countByPhoneNumberAndCallStatus(String phoneNumber, Boolean callStatus);
    Set<Communication> findByPhoneNumberContainsAndCallStatus(String phoneNumber, Boolean callStatus);
    Long countByPhoneNumberContainsAndCallStatus(String phoneNumber, Boolean callStatus);


    //course Crn
    Set<Communication> findByCourseInterestedCRNAndCallStatus(long crn,Boolean status);
    Long countByCourseInterestedCRNAndCallStatus(long crn,Boolean status);

    //course name
    Set<Communication> findByCourseInterestedAndCallStatus(String courseName,Boolean status);
    Long countByCourseInterestedAndCallStatus(String courseName,Boolean status);

    //name
    Iterable<Communication> findByNameLikeIgnoreCaseAndCallStatusTrue(String name);
    Iterable<Communication> findByNameLikeIgnoreCaseAndCallStatusFalse(String name);
    Long countByNameLikeIgnoreCaseAndCallStatusTrue(String name);
    Long countByNameLikeIgnoreCaseAndCallStatusFalse(String name);
}
