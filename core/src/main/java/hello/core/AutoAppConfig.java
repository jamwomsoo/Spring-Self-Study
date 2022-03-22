package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration

// @를 찾아서 자동으로 스프링 빈으로 등록해준다
// AppConfig 막기
// Filter를 쓰는 이유 -> 기존의 예제코드를 최개한 남기고 유지하기 위해서 이 방법을 선택했다.
@ComponentScan( excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class))
public class AutoAppConfig {

    // 애플리케이션의 실제 코드와 관계없는 테스트 코드 - 필드 주입
//    @Autowired MemberRepository memberRepository;
//    @Autowired DiscountPolicy   discountPolicy;
//    @Bean
//    OrderService orderService(MemberRepository memberRepository,  DiscountPolicy discountPolicy){
//        return new OrderServiceImpl(memberRepository, discountPolicy);
//    }

    //충돌 테스트 (자동 VS 수동)
/*    @Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }*/

}
