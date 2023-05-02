package project.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.*;
import project.shop.repository.ItemRepository;
import project.shop.repository.MemberRepository;
import project.shop.repository.OrderItemRepository;
import project.shop.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService{

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public Order save(Order order) {

        return orderRepository.save(order);
    }


    public Order orderItem(Long memberId, Long itemId, int quantity) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();
        OrderItem orderItem = new OrderItem(item, quantity);
        orderItemRepository.save(orderItem);
        List<Order> orders = member.getOrders();

        //여기서 기존 unpaidOrder에 orderItem 잘 추가하고, 이상하게 order를 하나 더 만들어 버림.. 빈거
        Order unpaidOrder = orders.stream().filter(order -> order.getStatus().equals(OrderStatus.UNPAID)).findFirst().orElse(new Order(member));

        unpaidOrder.addOrderItem(orderItem);
        orderRepository.save(unpaidOrder);

        return unpaidOrder;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public Order order(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.PAID);
        order.setDelivery(Delivery.READY);
        return order;
    }

    public void orderPaymentDone(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.PAID);
        order.setDelivery(Delivery.READY);
    }
}
