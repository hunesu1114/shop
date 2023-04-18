package project.shop.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    //단일 상품 주문 갯수
    private int quantity;

    public int getPrice() {
        return this.item.getPrice()*this.quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
