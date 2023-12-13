package com.example.advanced.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.advanced.OrderDTO;
import com.example.advanced.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@GetMapping("/v0/order")
	public String order(String itemId){
		orderService.makeOrder(new OrderDTO(itemId));
		return "200/ok";
	}
}
