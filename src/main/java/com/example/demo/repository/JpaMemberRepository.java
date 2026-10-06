package com.example.demo.repository;

import com.example.demo.domain.Member;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;



public class JpaMemberRepository implements MemberRepository {

  private final EntityManager em;// jpa는 모두 EntityManager 라는 것으로 동작합니다!!
  // 그래들에 data-jpa 어쩌구를 임플리먼트 해서 라이브러리를 받았잖아요? 그래서 스프링 부트가 자동으로 EntityManager를 생성해줍니다.
  //우린 이걸 injection 받으면 됩니다.

  public JpaMemberRepository(EntityManager em) {
    this.em = em;
  }

  @Override
  public Member save(Member member) {
    em.persist(member);
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    Member member= em.find(Member.class,id);
    return Optional.ofNullable(member);//단건을 찾는 것, pk로 찾는 것은 이렇게 사용할 수 있는데
    //나머지는 jpql을 활용하여 짜야 합니다.
  }

  @Override
  public Optional<Member> findByName(String name) {
    List<Member> result = em.createQuery("select m from Member m where m.name= :name", Member.class)
                           .setParameter("name", name)
                           .getResultList();

    return result.stream().findAny();
  }

  @Override
  public List<Member> findAll() {
    List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
    return result;
  }//select * from Member m 대신에 위에 처럼 객체 자체를 조회한다는 식으로 씁니다!!
}
