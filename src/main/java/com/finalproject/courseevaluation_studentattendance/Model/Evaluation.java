package com.finalproject.courseevaluation_studentattendance.Model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Evaluation")
public class Evaluation {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //excellent/above average/ average/ fair/ poor
    @NotEmpty
    private String content;
    @NotEmpty
    private String quality;
    @NotEmpty
    private String experience;
    @NotEmpty
    private String materials;
    @NotEmpty
    private String environment;
    @NotEmpty
    private String equipment;

    // text area to add what they want
    private String likes;
    private String dislikes;
    private String suggestions;
    private String otherClass;
    private String findings;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Course courseEvaluation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public String getOtherClass() {
        return otherClass;
    }

    public void setOtherClass(String otherClass) {
        this.otherClass = otherClass;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public Course getCourseEvaluation() {
        return courseEvaluation;
    }

    public void setCourseEvaluation(Course courseEvaluation) {
        this.courseEvaluation = courseEvaluation;
    }




}