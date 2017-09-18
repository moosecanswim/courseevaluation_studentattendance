package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Communication;
import com.finalproject.courseevaluation_studentattendance.Repositories.CommunicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunicationService {
    @Autowired
    CommunicationRepository communicationRepository;

    public void create(Communication aComm){
        Communication existCom = communicationRepository.findOne(aComm.getId());
        if (existCom==null) {

            System.out.println("CommunicationService: Adding communication to repository");
            communicationRepository.save(aComm);
        }
        else{
            System.out.println("CommunicationService: the communication you wanted to enter is already in the repository");
        }
    }

    public void update(Communication aCom){
        Communication existCom = communicationRepository.findOne(aCom.getId());
        if (existCom==null){
            System.out.println("CommunicationService: the communication you wanted to enter is already in the repository");
        }
        else{
            existCom.setId(aCom.getId());
            existCom.setmNumber(aCom.getmNumber());
            existCom.setPhoneNumber(aCom.getPhoneNumber());
            existCom.setEmail(aCom.getEmail());
            existCom.setCourseInterested(aCom.getCourseInterested());
            existCom.setCallDetails(aCom.getCallDetails());
            System.out.println("Adding " + existCom.toString());
            communicationRepository.save(existCom);
        }
    }public Iterable<Communication> showAll(){
        return communicationRepository.findAll();
    }
    public Iterable<Communication> showAllAvalible(){
        return communicationRepository.findAllByCallStatus(true);
    }
    public Iterable<Communication> showAllUnavalible(){
        return communicationRepository.findAllByCallStatus(false);
    }

    //toggle call status
    public void toggleCommunicationStatus(Communication aCom){
        Communication existCom = communicationRepository.findOne(aCom.getId());
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


}
