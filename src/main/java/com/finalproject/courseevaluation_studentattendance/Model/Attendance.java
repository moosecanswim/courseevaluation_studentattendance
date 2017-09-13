package com.finalproject.courseevaluation_studentattendance.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private Date date;
    private String status;

    @ManyToMany(mappedBy = "attendances")
    private Set<Person>personAttendances;

    @ManyToMany(mappedBy = "courseAttendances")
    private Set<Course>attendanceCourses;

    public Attendance(){
        this.personAttendances=new HashSet<Person>();
        this.attendanceCourses=new HashSet<Course>();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Person> getPersonAttendances() {
        return personAttendances;
    }

    public void setPersonAttendances(Set<Person> personAttendances) {
        this.personAttendances = personAttendances;
    }

    public void addPerson(Person stud){

        personAttendances.add(stud);
    }
    public void addCourse(Course crs){
        attendanceCourses.add(crs);
    }
}
