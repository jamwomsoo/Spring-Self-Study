package hello.core.singleton;

/*
* 
* 싱글톤 패턴
* 
* 싱글톤 패턴 객체 인스턴스  instance는 해당 클래스 내부에서만 생성할 수 있다.(생성자가 Private이기 떄문에 외부에서 생성불가)
* 
* 
* 
* */


public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출출");
    }
}

