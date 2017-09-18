package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Communication;
import com.finalproject.courseevaluation_studentattendance.Model.Course;
import com.finalproject.courseevaluation_studentattendance.Repositories.CommunicationRepository;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class CommunicationService {
    @Autowired
    CommunicationRepository communicationRepository;
    @Autowired
    CourseService courseService;

    public void create(Communication aComm){
        Communication existCom = communicationRepository.findOne(aComm.getId());
        if (existCom==null) {

            System.out.println("CommunicationService: Adding communication to repository");
            aComm.setCourseInterested(courseService.findByCRN(Long.valueOf(aComm.getCourseInterestedCRN())));
            communicationRepository.save(aComm);
        }
        else{
            System.out.println("CommunicationService: the communication you wanted to enter is already in the repository");
        }
    }

    public void update(Communication aComm){
        Communication existCom = communicationRepository.findOne(aComm.getId());
        if (existCom==null){
            System.out.println("CommunicationService: the communication you wanted to enter is already in the repository");
        }
        else{
            existCom.setId(aComm.getId());
            existCom.setmNumber(aComm.getmNumber());
            existCom.setPhoneNumber(aComm.getPhoneNumber());
            existCom.setEmail(aComm.getEmail());
            //existCom.setCourseInterested(courseService.findByCRN(Long.valueOf(aComm.getCourseInterestedCRN())));
            existCom.setCallDetails(aComm.getCallDetails());
            System.out.println("Adding " + existCom.toString());
            communicationRepository.save(existCom);
        }
    }

    public Iterable<Communication> showAllAvalible(){
        return communicationRepository.findAllByCallStatus(true);
    }
    public Iterable<Communication> showAllUnavalible(){
        return communicationRepository.findAllByCallStatus(false);
    }

    //toggle call status
    public void toggleCommunicationStatus(Communication aComm){
        Communication existCom = communicationRepository.findOne(aComm.getId());
        if(existCom ==null){
            System.out.println("CommunicationService: Cannot toggle status of communication (no id cannot find)");
        }
        else{
            if(existCom.getCallStatus()==true){
                existCom.setCallStatus(false);
            }
            else{
                existCom.setCallStatus(true);
            }

        }
        communicationRepository.save(existCom);
    }
    //////////////FIND THINGS///////////
    public Communication findOne(long communicationId){
        return communicationRepository.findOne(communicationId);
    }
    public Iterable<Communication> showByStatus(Boolean status){
        return communicationRepository.findAllByCallStatus(status);
    }
    //mNumber
    public Iterable<Communication> findByMNumberAndStatus(String mNumber, Boolean status){
        String first=mNumber.substring(0);
        if(!first.equalsIgnoreCase("m")){
        }
        else{
            mNumber="M"+mNumber;
        }
        if(status==true){
            return communicationRepository.findByMNumberLikeAndCallStatusTrue("%"+mNumber+"%");
        }
        else{
            return communicationRepository.findByMNumberLikeAndCallStatusFalse("%"+mNumber+"%");
        }


    }
    //email
    public Iterable<Communication> findByEmailAndStatus(String email, Boolean status){
        if(status==true){
            return communicationRepository.findByEmailLikeAndCallStatusTrue("%"+email+"%");
        }
        else{
            return communicationRepository.findByEmailLikeAndCallStatusFalse("%"+email+"%");
        }
    }
    //phone number
    public Iterable<Communication> findByPhoneNumberAndStatus(String phoneNumber, Boolean status){
        if(status==true){
            return communicationRepository.findByPhoneNumberLikeAndCallStatusTrue("%"+phoneNumber+"%");
        }
        else{
            return communicationRepository.findByPhoneNumberLikeAndCallStatusFalse("%"+phoneNumber+"%");
        }
    }

    //by crn
    public Iterable<Communication> findByCrnAndStatus(String crn, Boolean status){
        if(status==true){
            return communicationRepository.findByCourseInterestedCRNLikeAndCallStatusTrue(crn);
        }
        else{
            return communicationRepository.findByCourseInterestedCRNLikeAndCallStatusFalse(crn);
        }

    }
//    //find by course name
//    public Iterable<Communication> findByCourseNameAndStatus(String courseName,Boolean status){
//        ArrayList<String> theCRNs = new ArrayList<String>();
//
//        Iterable<Course> coursesAll = courseService.findByCourseName("%"+courseName+"%");
//        if(coursesAll.equals(null)){
//            System.out.println("CommService-findByCourseNameAndStatus: no courses with that name");
//        }else {
//
//            for(Course course:coursesAll){
//                //do nothing
//                theCRNs.add(String.valueOf(course.getCrn()));
//            }
//        }
//            if(status==true){
//            for(String l:theCRNs){
//                Iterable<Communication> lList = findByCrnAndStatus(l,true);
//                }
//                return communicationRepository.findByCourseInterestedLikeAndCallStatusTrue(courseName);
//            }
//            else{
//                return communicationRepository.findByCourseInterestedLikeAndCallStatusFalse(courseName);
//            }
//
//
//    }

    //contact name
    public Iterable<Communication> findByNameAndAvalible(String name){
        long count = communicationRepository.countByNameLikeIgnoreCaseAndCallStatusTrue(name);
        System.out.println("commService: there are " + count + " occurances of " +name +" true");
        return communicationRepository.findByNameLikeIgnoreCaseAndCallStatusTrue(name);
    }
    public Iterable<Communication> findByNameAndUnavalible(String name) {
        long count = communicationRepository.countByNameLikeIgnoreCaseAndCallStatusFalse(name);
        System.out.println("commService: there are " + count + " occurances of " +name +"false");
        return communicationRepository.findByNameLikeIgnoreCaseAndCallStatusFalse(name);
    }
}
