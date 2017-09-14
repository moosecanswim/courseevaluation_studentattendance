package com.finalproject.courseevaluation_studentattendance.Model;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String mNumber;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;
    private Date startDate;
    private Boolean active;//true if user is active false if user has been archived

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(joinColumns = @JoinColumn(name="person_id"),
   inverseJoinColumns=@JoinColumn(name="role_id"))

  private Set<PersonRole>personRoles;
  @ManyToMany
  private Set<Course>courseInstructor;

  @OneToMany(mappedBy = "personAttendance")
  private Set<Attendance>attendances;

  @ManyToOne
  private Course courseStudent;

    public Person()
    {
        this.active=true;
        this.personRoles=new HashSet<PersonRole>();
        this.courseInstructor=new HashSet<Course>();
        this.attendances=new HashSet<Attendance>();
        this.mNumber="M---";
    }
    public Person(String firstName, String lastName, String username, String password, String email){
        this.active=true;
        this.personRoles=new HashSet<PersonRole>();
        this.courseInstructor=new HashSet<Course>();
        this.attendances=new HashSet<Attendance>();
        this.mNumber="M---";
    }

//    @Override
//    public String toString() {
//        return "Person{" +
//                "id=" + id +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", email='" + email + '\'' +
//                ", role=" + role +
//                ", course=" + course +
//                '}';
//    }

    public void addCourse(Course course)
    {


    }

//    public void removeCourse(Course course1)
//    {
//    }
//    public void deleteCourse(Course course)
//    {
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public Set<PersonRole> getPersonRoles() {
        return personRoles;
    }

    public void setPersonRoles(Set<PersonRole> personRoles) {
        this.personRoles = personRoles;
    }


    public Set<Course> getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(Set<Course> courseInstructor) {
        this.courseInstructor = courseInstructor;
    }


    public Set<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(Set<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void addRole(PersonRole p)
    {
        this.personRoles.add(p);
    }

    public void addInstructor(Course in){
        this.courseInstructor.add(in);
    }

    public void addAttendance(Attendance atn)
    {
        this.attendances.add(atn);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
