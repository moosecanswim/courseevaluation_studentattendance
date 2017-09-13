package com.finalproject.courseevaluation_studentattendance.Model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //@NotEmpty
    private String roleName;

//    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
//    private Set<Person> persons;

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

//    public Set<Person> getPersons() {
//        return persons;
//    }
//
//    public void setPersons(Set<Person> persons) {
//        this.persons = persons;
//    }
//
//    //Constructor for Role Class
//    public Role()
//    {
//        this.persons = new HashSet<>();
//    }

    //add person
//    public void addPersontoRole(Person p)
//    {
//        this.persons.add(p);
//    }
//
//    //remove person
//    public void removePersonfromRole(Person p)
//    {
//        this.persons.remove(p);
//    }


}
