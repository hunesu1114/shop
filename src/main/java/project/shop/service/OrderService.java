package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.*;
import project.shop.repository.ItemRepository;
import project.shop.repository.MemberRepository;
import project.shop.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Order order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();
        OrderItem orderItem = new OrderItem(item, count);
        List<Order> orders = member.getOrders();
        Order unpaidOrder = orders.stream().filter(order -> order.getStatus().equals(OrderStatus.UNPAID)).findFirst().orElseThrow();
        if(unpaidOrder==null){
            Order newOrder = new Order(member);
            newOrder.addOrderItem(orderItem);
            return newOrder;
        } else{
            unpaidOrder.addOrderItem(orderItem);
            return unpaidOrder;
        }
    }

    public void orderPaymentDone(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.PAID);
        order.setDelivery(Delivery.READY);
    }
}
