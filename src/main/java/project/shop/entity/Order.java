package project.shop.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public int getTotalPrice() {
        int sum=0;
        for (OrderItem orderItem : orderItems) {
            sum += orderItem.getTotalPrice();
        }
        return sum;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public Order(Member member) {
        this.member = member;
        member.getOrders().add(this);
        this.status = OrderStatus.UNPAID;
    }
}
