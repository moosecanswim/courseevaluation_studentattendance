package com.finalproject.courseevaluation_studentattendance.Model;

import javax.persistence.*;

@Entity
@Table(name="Evaluation")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //need to add relationship with course

    //excellent/above average/ average/ fair/ poor
    private String content;
    private String quality;
    private String experience;
    private String materials;
    private String environment;
    private String equipment;

    // text area to add what they want
    private String likes;
    private String dislikes;
    private String suggestions;
    private String otherClass;
    private String findings;

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
}