package com.example.advanced.repository;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	public void save(String itemId){
		if(itemId.equals("exId")){
			throw new IllegalStateException("");
		}
		//1sec 지연 발생
		sleep(1000);
	}

	private void sleep(int millis){
		try{
			Thread.sleep(millis);
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}
