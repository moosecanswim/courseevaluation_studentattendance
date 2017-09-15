package com.finalproject.courseevaluation_studentattendance;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEmailTools
public class CourseevaluationStudentattendanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseevaluationStudentattendanceApplication.class, args);
	}
}
