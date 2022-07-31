package hello.itemservice.domain;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Item {

    // DB에서 값을 증가시켜줌
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", length = 10)
    private String itemName;
    private Integer price;     // 테이블 컬럼명이랑 같으면 생략가능
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
