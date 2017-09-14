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

    private long crn;

    private String courseName;

    private Date startDate;

    private Date endDate;

    private boolean status;

    @ManyToOne
    private Person instructor;

    @OneToMany(mappedBy = "attendanceCourse")
    private Set<Attendance>courseAttendances;

    @OneToMany(mappedBy = "courseEvaluation")
    private Set<Evaluation>evaluations;

    @OneToMany(mappedBy = "courseStudent")
    private Set<Person>student;


    public Course(){
        setStatus(true);
        setEvaluations(new HashSet<Evaluation>());
        setCourseAttendances(new HashSet<Attendance>());
        setStudent(new HashSet<Person>());

    }

    public Course(long crn, String aCourseName, Date aStartDate, Date anEndDate, Person instructor){
        this.crn=crn;
        this.courseName=aCourseName;
        this.startDate=aStartDate;
        this.endDate=anEndDate;
        setStatus(true);
        this.instructor=instructor;
        setEvaluations(new HashSet<Evaluation>());
        setCourseAttendances(new HashSet<Attendance>());
        setStudent(new HashSet<Person>());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCrn() {
        return crn;
    }

    public void setCrn(long crn) {
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Person getInstructor() {
        return instructor;
    }

    public void setInstructor(Person instructor) {
        this.instructor = instructor;
    }

    public Set<Person> getStudent() {
        return student;
    }

    public void setStudent(Set<Person> student) {
        this.student = student;
    }

    public Set<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Set<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public Set<Attendance> getCourseAttendances() {
        return courseAttendances;
    }

    public void setCourseAttendances(Set<Attendance> courseAttendances) {
        this.courseAttendances = courseAttendances;
    }

    public  void addStudent(Person stud){
        this.student.add(stud);
    }

    public  void addEvaluation(Evaluation eva)
    {
        this.evaluations.add(eva);
    }
    public void addAttendance(Attendance att){
        this.courseAttendances.add(att);
    }
}
