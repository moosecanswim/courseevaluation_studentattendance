package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Communication;
import com.finalproject.courseevaluation_studentattendance.Repositories.CommunicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Iterable<Communication> findByMNumberAndStatus(String mNumber, Boolean status){
        return communicationRepository.findByMNumberAndCallStatus(mNumber,status);
    }
    public Iterable<Communication> findByEmailAndStatus(String email, Boolean status){
        return communicationRepository.findByEmailAndCallStatus(email,status);
    }
    public Iterable<Communication> findByPhoneNumberAndStatus(String phoneNumber, Boolean status){
        return communicationRepository.findByPhoneNumberContainsAndCallStatus(phoneNumber,status);
    }
    public Iterable<Communication> findByCrnAndStatus(Long crn, Boolean status){
        return communicationRepository.findByCourseInterestedCRNContainsAndCallStatus(crn,status);
    }
    public Iterable<Communication> findByCourseNameAndStatus(String courseName, Boolean status){
        return communicationRepository.findByCourseInterestedContainsAndCallStatus(courseName,status);
    }


}
