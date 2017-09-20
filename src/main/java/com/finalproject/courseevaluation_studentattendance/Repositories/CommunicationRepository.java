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
    Set<Communication> findByMNumberLikeAndCallStatusTrue(String mNumber);
    Long countByMNumberLikeAndCallStatusTrue(String mNumber);
    Set<Communication> findByMNumberLikeAndCallStatusFalse(String mNumber);
    Long countByMNumberLikeAndCallStatusFalse(String mNumber);

    //Email
    Set<Communication> findByEmail(String email);
    Long countByEmail(String email);
    Set<Communication> findByEmailLikeAndCallStatusTrue(String email);
    Long countByEmailLikeAndCallStatusTrue(String email);
    Set<Communication> findByEmailLikeAndCallStatusFalse(String email);
    Long countByEmailLikeAndCallStatusFalse(String email);


    //phoneNumber
    Set<Communication> findByPhoneNumber(String phoneNumber);
    Long countByPhoneNumber(String phoneNumber);
    Set<Communication> findByPhoneNumberLikeAndCallStatusTrue(String phoneNumber);
    Long countByPhoneNumberLikeAndCallStatusTrue(String phoneNumber);
    Set<Communication> findByPhoneNumberLikeAndCallStatusFalse(String phoneNumber);
    Long countByPhoneNumberLikeAndCallStatusFalse(String phoneNumber);


    //course Crn
    Set<Communication> findByCourseInterestedCRNLikeAndCallStatusTrue(String crn);
    Long countByCourseInterestedCRNLikeAndCallStatusTrue(String crn);
    Set<Communication> findByCourseInterestedCRNLikeAndCallStatusFalse(String crn);
    Long countByCourseInterestedCRNLikeAndCallStatusFalse(String crn);

    //course name
    Set<Communication> findByCourseInterestedLikeAndCallStatusTrue(String courseName);
    Long countByCourseInterestedLikeAndCallStatusTrue(String courseName);
    Set<Communication> findByCourseInterestedLikeAndCallStatusFalse(String courseName);
    Long countByCourseInterestedLikeAndCallStatusFalse(String courseName);

    //name
    Iterable<Communication> findByNameLikeIgnoreCaseAndCallStatusTrue(String name);
    Iterable<Communication> findByNameLikeIgnoreCaseAndCallStatusFalse(String name);
    Long countByNameLikeIgnoreCaseAndCallStatusTrue(String name);
    Long countByNameLikeIgnoreCaseAndCallStatusFalse(String name);


}
