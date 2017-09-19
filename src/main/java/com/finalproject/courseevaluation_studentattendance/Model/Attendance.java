package com.finalproject.courseevaluation_studentattendance.Model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

//    private Date date;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private String date;
    @NotEmpty
    private String status;

    @NotEmpty
    @ManyToOne
    private Person personAttendance;
    @NotEmpty
    @ManyToOne
    private Course attendanceCourse;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Person getPersonAttendance() {
        return personAttendance;
    }

    public void setPersonAttendance(Person personAttendances) {
        this.personAttendance = personAttendances;
    }

    public Course getAttendanceCourse() {
        return attendanceCourse;
    }

    public void setAttendanceCourse(Course attendanceCourses) {
        this.attendanceCourse = attendanceCourses;
    }

}
