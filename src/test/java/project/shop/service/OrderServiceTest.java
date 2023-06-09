package project.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.*;
import project.shop.repository.OrderRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ItemService itemService;

    @Before
    public void initData() {
        Member member1 = Member.builder().email("email1").name("name1").picture("pic1").role(Role.GUEST).build();
        Member member2 = Member.builder().email("email2").name("name2").picture("pic2").role(Role.GUEST).build();
        memberService.save(member1);
        memberService.save(member2);
        log.info("member1 ID : {}",member1.getId());
        log.info("member2 ID : {}",member2.getId());

        Item item1 = Item.builder().name("상품1").price(10000).feature("설명1").member(member1).build();
        Item item2 = Item.builder().name("상품2").price(20000).feature("설명2").member(member1).build();
        itemService.save(item1);
        itemService.save(item2);
        log.info("item1 ID : {}",item1.getId());
        log.info("item2 ID : {}",item2.getId());

    }

    @Test
    public void save() {
        Order order = new Order(memberService.findByEmail("email1"));
        Order savedOrder = orderService.save(order);
        assertThat(order.getMember().getEmail()).isEqualTo(savedOrder.getMember().getEmail());
    }

    @Test
    public void orderItem() {
        Member buyer = memberService.findByEmail("email2");
        Item item1 = itemService.findById(3L);
        Item item2 = itemService.findById(4L);
        orderService.orderItem(buyer.getId(), item1.getId(), 3);
        for (Order o : buyer.getOrders()) {
            log.info("order id : {}", o.getId());
            log.info("order member : {}", o.getMember().getName());
            log.info("order totalPrice : {}", o.getTotalPrice());
            log.info("order status : {}", o.getStatus());
            for (OrderItem oi : o.getOrderItems()) {
                log.info("orderItem Id : {}", oi.getId());
                log.info("orderItem itemName : {}", oi.getItem().getName());
                log.info("orderItem itemPrice : {}", oi.getItem().getPrice());
                log.info("orderItem quantity : {}", oi.getQuantity());
            }
        }
        log.info("==========================================");
        orderService.orderItem(buyer.getId(), item2.getId(), 4);    //여기서 order가 새로운게 하나 더 생김
        for (Order o : buyer.getOrders()) {
            log.info("order id : {}", o.getId());
            log.info("order member : {}", o.getMember().getName());
            log.info("order totalPrice : {}", o.getTotalPrice());
            log.info("order status : {}", o.getStatus());
            for (OrderItem oi : o.getOrderItems()) {
                log.info("orderItem Id : {}", oi.getId());
                log.info("orderItem itemName : {}", oi.getItem().getName());
                log.info("orderItem itemPrice : {}", oi.getItem().getPrice());
                log.info("orderItem quantity : {}", oi.getQuantity());
            }
        }
        log.info("==========================================");

        Order order = buyer.getOrders().get(0);
        OrderItem orderItem1 = order.getOrderItems().get(0);
        OrderItem orderItem2 = order.getOrderItems().get(1);

        assertThat(orderItem1.getItem().getName()).isEqualTo("상품1");
        assertThat(orderItem2.getItem().getName()).isEqualTo("상품2");

        assertThat(orderItem1.getOrder().getId()).isEqualTo(1L);
        assertThat(orderItem2.getOrder().getId()).isEqualTo(1L);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.UNPAID);


    }

    @Test
    public void order() {
    }

    @Test
    public void orderPaymentDone() {
    }
}