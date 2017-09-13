package com.finalproject.courseevaluation_studentattendance.Model;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String courseName;

    private Date startDate;

    private Date endDate;

    private boolean status;

    @ManyToMany(mappedBy = "courseInstructor")
    private Set<Person> instructor;

    @ManyToMany(mappedBy = "courseStudent")
    private Set<Person>student;


    public Course(){
        this.instructor=new HashSet<Person>();
        this.student=new HashSet<Person>();
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Person> getInstructor() {
        return instructor;
    }

    public void setInstructor(Set<Person> instructor) {
        this.instructor = instructor;
    }

    public Set<Person> getStudent() {
        return student;
    }

    public void setStudent(Set<Person> student) {
        this.student = student;
    }

    public void addInstructor(Person ins){
        instructor.add(ins);
    }
    public  void addStudent(Person stud){
        student.add(stud);
    }
}
