package com.finalproject.courseevaluation_studentattendance.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Attendence {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private Date date;
    private String status;

}
