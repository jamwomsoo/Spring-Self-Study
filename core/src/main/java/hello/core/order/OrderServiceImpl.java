package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final을 파라미터로 받는 자동으로 생성자를 만들어줌
public class OrderServiceImpl implements OrderService{

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

/*    //필드 주입
    @Autowired private MemberRepository memberRepository;
    @Autowired private DiscountPolicy   discountPolicy;
    public void setMemberRepository(MemberRepository memberRepository){
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy = discountPolicy;
    }*/


/*  //Setter 주입
    private MemberRepository memberRepository;
    private DiscountPolicy   discountPolicy;

    @Autowired(required = false) //required = false ->  선택적
    public void setMemberRepository(MemberRepository memberRepository){
        System.out.println("memberRepository = " + memberRepository);
        this.memberRepository = memberRepository;
    }
    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy){
        System.out.println("discountPolicy = " + discountPolicy);
        this.discountPolicy   = discountPolicy;
    }*/
    //setter로 의존관계 주입시 생성자 없어도 됨

    //생성자 주입
    // final -> 생성자에서만 값을 넣어줄 수 있다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy   discountPolicy;




    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        System.out.println("1. OrderServiceImpl.orderServiceImpl");
        this.memberRepository = memberRepository;
        this.discountPolicy   = discountPolicy;
    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member     = memberRepository.findById(memberId); //회원 정보 조회
        int discountPrice = discountPolicy.discount(member, itemPrice); //회원의 정보를 넘겨서 할인 정책 조회

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

/*    @Autowired
    public  void init(MemberRepository memberRepository, DiscountPolicy discountPolicy){
        this.memberRepository = memberRepository;
        this.discountPolicy   =discountPolicy;
    }*/


}
