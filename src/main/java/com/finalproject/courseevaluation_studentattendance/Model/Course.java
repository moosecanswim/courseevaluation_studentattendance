package com.finalproject.courseevaluation_studentattendance.Model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String crn;

    private String courseName;

    private Date startDate;

    private Date endDate;

    private boolean courseCompleteStatus;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public boolean isCourseCompleteStatus() {
        return courseCompleteStatus;
    }

    public void setCourseCompleteStatus(boolean courseCompleteStatus) {
        this.courseCompleteStatus = courseCompleteStatus;
    }


}
