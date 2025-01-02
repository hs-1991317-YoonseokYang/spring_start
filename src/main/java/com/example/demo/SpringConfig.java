package com.example.demo;

import com.example.demo.repository.JdbcMemberRepository;
import com.example.demo.repository.JdbcTemplateMemberRepository;
import com.example.demo.repository.JpaMemberRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoryMemberRepository;
import com.example.demo.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import jakarta.persistence.EntityManager;


@Configuration
public class SpringConfig {

  private final MemberRepository memberRepository;

  private EntityManager em;

  @Autowired
  public SpringConfig(MemberRepository memberRepository, EntityManager em) {
    this.memberRepository = memberRepository;
    this.em = em;
  }

  //
//  private DataSource dataSource;
//
//  @Autowired
//  public SpringConfig(DataSource dataSource) {
//    this.dataSource = dataSource;
//  }

  @Bean
  public MemberService memberService() {
    return new MemberService(memberRepository);
  }

//  @Bean
//  public MemberRepository memberRepository(){
//    //return new MemoryMemberRepository();
//    //return new JdbcMemberRepository(dataSource);
//    //return new JdbcTemplateMemberRepository(dataSource);
//    //return new JpaMemberRepository(em);
//
//  }
}
