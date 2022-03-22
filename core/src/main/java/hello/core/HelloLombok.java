package hello.core;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 생성자 자동으로 만들어줌
public class HelloLombok {

    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("asdsa");
        String name = helloLombok.getName();
        System.out.println("name = " + name);
        System.out.println("helloLombok = " + helloLombok);
    }
}
