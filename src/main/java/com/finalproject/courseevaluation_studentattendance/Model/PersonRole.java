package com.finalproject.courseevaluation_studentattendance.Model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PersonRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String roleName;

    @ManyToMany(mappedBy = "personRoles", fetch = FetchType.LAZY)
    private Set<Person> people;

    public PersonRole(){

        this.people =new HashSet<Person>();
    }
    public PersonRole(String aRoleName){
        this.people=new HashSet<Person>();
        this.roleName=aRoleName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public void addPerson(Person usr){

        people.add(usr);
    }

}


