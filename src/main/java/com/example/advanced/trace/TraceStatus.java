package com.example.advanced.trace;

import lombok.Getter;

@Getter
public class TraceStatus {
	private TraceId traceId;
	private int traceLevel;
	private long startMillis;

	public TraceStatus(TraceId traceId, int traceLevel, long startMillis) {
		this.traceId = traceId;
		this.traceLevel = traceLevel;
		this.startMillis = startMillis;
	}

	public TraceStatus(TraceId traceId, long startMillis) {
		this.traceId = traceId;
		this.startMillis = startMillis;

		traceLevel = 0;
	}

	public void completeCurrLevel() {
		this.traceLevel--;
	}

	public String getTraceId() {
		return traceId.getId();
	}
}
