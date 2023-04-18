package project.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.shop.entity.Member;
import project.shop.repository.ItemRepository;
import project.shop.repository.MemberRepository;
import project.shop.repository.OrderRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

}
