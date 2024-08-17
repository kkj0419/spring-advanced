package com.example.advanced.trace;

import java.util.UUID;

import lombok.Getter;

@Getter
public class TraceId {
	private String id;

	private TraceId() {
		id = createId();
	}

	public static TraceId createTraceId() {
		return new TraceId();
	}

	private String createId(){
		return UUID.randomUUID().toString().substring(0, 8);
	}


}
