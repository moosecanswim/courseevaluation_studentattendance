package com.finalproject.courseevaluation_studentattendance.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Communication {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    //link to student (one to one)
    private String mNumber;
    private String phoneNumber;
    private String email;

    //link to course time allows (one to many)
    @ManyToOne
    @JoinColumn(name="course_id")
    private Course courseInterested;
    private String courseInterestedCRN;
    private String callDetails;
    private Boolean callStatus;
    private String createdOn;

    public Communication(){
        setCallStatus(true);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public Course getCourseInterested() {
        return courseInterested;
    }

    public void setCourseInterested(Course courseInterested) {
        this.courseInterested = courseInterested;
    }

    public String getCallDetails() {
        return callDetails;
    }

    public void setCallDetails(String callDetails) {
        this.callDetails = callDetails;
    }

    @Override
    public String toString() {
        return "Communication{" +
                "id=" + id +
                ", mNumber='" + mNumber + '\'' +
                ", courseInterested=" + courseInterested +
                ", callDetails='" + callDetails + '\'' +
                '}';
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(Boolean callStatus) {
        this.callStatus = callStatus;
    }

    public String getCourseInterestedCRN() {
        return courseInterestedCRN;
    }

    public void setCourseInterestedCRN(String courseInterestedCRN) {
        this.courseInterestedCRN = courseInterestedCRN;
        //set the course interested here?
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
