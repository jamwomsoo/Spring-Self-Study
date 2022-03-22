package hello.core.beanFind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationContextBeanFIndTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 빈 타입으로만 조회")
    void findBeanByType(){
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    // 이 코드는 좋은 코드는 아님 -> 구체 타입으로 작성하면 유연성이 떨어진다
    // 구현(인스턴스 타입)에 의존한 케이스
    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2(){
        MemberService memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }
    
    // 실패 테스트
    @Test
    @DisplayName("빈 이름으로 조회 X")
    void findBeanByNameX(){
        // ac.getBean("XXXX", MemberService.class);
        //MemberService xxxx = ac.getBean("XXXX", MemberService.class);
        assertThrows(NoSuchBeanDefinitionException.class,
                     () -> ac.getBean("XXXX", MemberService.class));
    }
}
