package com.example.advanced.trace;

import lombok.Getter;

@Getter
public class TraceStatus {
	private TraceId traceId;
	private TraceStatus preStatus;
	private int traceLevel;
	private long startMillis;

	public TraceStatus(TraceId traceId) {
		this.traceId = traceId;
		this.startMillis = System.currentTimeMillis();

		traceLevel = 0;
	}

	public TraceStatus(TraceStatus preStatus){
		this.preStatus = preStatus;
		this.traceId = preStatus.getTraceId();
		this.traceLevel = preStatus.getTraceLevel() + 1;
		this.startMillis = System.currentTimeMillis();
	}

	//todo  static vs non-static
	public static TraceStatus startNextLevel(TraceStatus status){
		return new TraceStatus(status);
	}

	public static TraceStatus completeCurrLevel(TraceStatus status) {
		return status.getPreStatus();
	}

	public TraceId getTraceId() {
		return traceId;
	}
}
