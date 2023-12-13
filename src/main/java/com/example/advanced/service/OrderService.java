package com.example.advanced.service;

import org.springframework.stereotype.Service;

import com.example.advanced.OrderDTO;
import com.example.advanced.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	public void makeOrder(OrderDTO orderDTO) {
		orderRepository.save(orderDTO.getOrderId());
	}

}
