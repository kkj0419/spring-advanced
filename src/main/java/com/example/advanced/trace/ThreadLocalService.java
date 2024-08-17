package com.example.advanced.trace;

import org.springframework.stereotype.Component;

@Component
public class ThreadLocalService {
	ThreadLocal<TraceStatus> traceStore = new ThreadLocal<>();

	public TraceStatus getCurrTrace(){
		return traceStore.get();
	}

	public void setCurrTrace(TraceStatus status){
		traceStore.set(status);
	}
}
