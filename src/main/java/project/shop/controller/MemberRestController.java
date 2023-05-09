package project.shop.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.shop.entity.Order;
import project.shop.entity.OrderItem;
import project.shop.service.OrderService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberRestController {

    private final OrderService orderService;
    /**
     * 모델을 DTO로 주고 받아야 함..
     * quantity 변경해도 order.html 페이지에 반영 x   -> ajax 좀 배워야 처리 가능 할 듯
     */
    @PostMapping("/{memberId}/cart")
    public void cart(@PathVariable Long memberId, @RequestParam("orderId") Long orderId,
                       @ModelAttribute Order order, HttpServletResponse response) throws IOException {
        Order orderEntity = orderService.findById(orderId);
        orderEntity.setOrderItems(order.getOrderItems());
        Order savedOrder = orderService.save(orderEntity);


        String redirectUrl = "/member/" + memberId + "/order?orderId=" + savedOrder.getId();
        response.sendRedirect(redirectUrl);
    }

//    @PostMapping("/{orderItemId}/quantityChange")
//    public void quantityChange(@PathVariable Long orderItemId, @RequestBody int quantity) {
//
//    }
}
