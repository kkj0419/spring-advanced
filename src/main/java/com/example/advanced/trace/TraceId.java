package com.example.advanced.trace;

import java.util.UUID;

import lombok.Getter;

@Getter
public class TraceId {
	private String id;
	private int level;

	private TraceId() {
		id = createId();
		level = 0;
	}

	public TraceId(String id, int level) {
		this.id = id;
		this.level = level;
	}

	public static TraceId createTraceId() {
		return new TraceId();
	}

	private String createId(){
		return UUID.randomUUID().toString().substring(0, 8);
	}


}
