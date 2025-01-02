package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.JdbcMemberRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.MemoryMemberRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired
    MemberRepository memberRepository; // JdbcMemberRepository로 하는게 아니라 MemberRepository로 하면 된다. ㄷㄷ
    @Autowired
    MemberService memberService;

    //테스트 할 때는 다른 곳에서 가져다 쓰는게 아니니까 컨스트럭터 인젝션 하지 않고 그냥 필드인젝션 받는게 편합니다.

    //memberService가 참조하는 레포지토리와 위에 선언한 레포지토리는 서로 다른 객체인데 이렇게 써도 돼?
    //레포지토리의 store을 static으로 선언했기 때문에 객체 간에 공유가 된다!! 따라서 가능(이건 나의 생각)
    //이 문제에 대해서 갓영한도 언급하고 있다. static 덕분에 지금은 크게 상관없지만 다르게 바꾸는게 좋다고 합니다.
    //service 클래스 코드를 수정합니다. repository를 외부에서 주입받도록!! DI(dependency injection)


//    @AfterEach
//    public void afterEach(){
//      memberRepository.clearStore();
//    } <- transactional 덕분에 이 코드가 필요 없어졌습니다. 그래서 주석 처리 헀어요.

    @Test
    void 회원가입() {
      //given
      Member member= new Member();
      member.setName("spring1");

      //when
      Long saveID = memberService.join(member);

      //then
      Member findMember = memberService.findOne(saveID).get();
      assertThat(member.getName()).isEqualTo(findMember.getName());


    }
    //테스트는 정상 플로우도 중요하지만 예외 플로우도 중요합니다. 따라서 회원 이름이 중복 되는 경우의 테스트 케이스도 짜는게
    //좋습니다.

    @Test
    public void 중복_회원_예외(){
      //given
      Member member1 = new Member();
      member1.setName("spring");
      Member member2 = new Member();
      member2.setName("spring");

      //when
      memberService.join(member1);
      IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

      assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");

    /* try{
      memberService.join(member2);
      fail("예외가 발생해야 합니다.");
    }
    catch (IllegalStateException e){
      //에러 터지면 성공
      assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다");
    }*/

      //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}

