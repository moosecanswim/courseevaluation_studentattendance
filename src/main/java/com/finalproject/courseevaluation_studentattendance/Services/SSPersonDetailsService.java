package com.finalproject.courseevaluation_studentattendance.Services;

import com.finalproject.courseevaluation_studentattendance.Model.Evaluation;
import com.finalproject.courseevaluation_studentattendance.Model.Person;
import com.finalproject.courseevaluation_studentattendance.Model.PersonRole;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationPagingService;
import com.finalproject.courseevaluation_studentattendance.Repositories.EvaluationRepository;
import com.finalproject.courseevaluation_studentattendance.Repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service
public class SSPersonDetailsService implements UserDetailsService{


    private EvaluationRepository evaluationRepository;

    private PersonRepository personRepository;

    public SSPersonDetailsService(PersonRepository personRepository){
        System.out.println("SSPersonDetailsService: constructor used.  repo size ("+personRepository.count()+")");
        this.personRepository=personRepository;
//        this.evaluationRepository=evaluationRepository;
    }

    public SSPersonDetailsService()
    {

    }
    @Override
    public UserDetails loadUserByUsername(String username){
        try{
            Person tempPerson=personRepository.findByUsername(username);
            if(tempPerson==null){
                System.out.println("SSPersonDetailService: user [" + username + "] not found with provided username");
                return null;
            }
            System.out.println("loadUserByUsername: have person" + tempPerson.toString());
            return new org.springframework.security.core.userdetails.User(tempPerson.getUsername(),tempPerson.getPassword(),getAuthorities(tempPerson));
        }
        catch(Exception e){
            System.out.println("exception! " +e.toString());
            System.out.println("SSPersonDetailService:User not found");
            throw new UsernameNotFoundException("SSPersonDetailService:User not found");
        }
    }
    private Set<GrantedAuthority> getAuthorities(Person user){
        System.out.println("getAuthorities has been entered");
        Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();

        for(PersonRole role:user.getPersonRoles()){
            GrantedAuthority grantedAuthority= new SimpleGrantedAuthority(role.getRoleName());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }

    SSPersonDetailsService(EvaluationRepository evaluationRepository){
        this.evaluationRepository = evaluationRepository;
    }


//    @Override
//    public Page<Evaluation> listAllByPage(Pageable pageable)
//    {
//        List<Evaluation> allBy = evaluationRepository.findAllBy(pageable);
//        return (Page<Evaluation>) allBy;
//
//
//    }
}