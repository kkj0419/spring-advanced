package com.example.advanced.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.advanced.trace.ThreadLocalService;
import com.example.advanced.trace.TraceId;
import com.example.advanced.trace.TraceStatus;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Aspect
public class LoggingAspect {

	private final String LOG_PREFIX = "|-->";
	private final String LOG_SUFFIX = "|<--";

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ThreadLocalService threadLocalService;

	// api 호출 시 logging
	// @Pointcut("execution(* com.example.advanced.api.*(..))")
	// public void logging(JoinPoint joinPoint) {

	// @Around("within(com.example.advanced.api.*)")
	@Around("execution(* com.example.advanced.api.*.*(..))")
	public void loggingAround(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("request start == ");

		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String methodName = signature.getMethod().getName();

		TraceStatus status = begin(methodName);
		//execute
		try {
			joinPoint.proceed();
			end(methodName, status);
			// logger.info("request end == " );
		} catch (Exception e) {
			exception(status, methodName, e);
			//비즈니스 로직 영향 없도록 throw
			throw e;
		}
	}

	private TraceStatus begin(String methodName){
		TraceStatus traceStatus = threadLocalService.getCurrTrace();
		if(traceStatus == null){
			traceStatus = new TraceStatus(TraceId.createTraceId());
		}else{
			traceStatus = new TraceStatus(traceStatus);
		}
		threadLocalService.setCurrTrace(traceStatus);
		logger.info(loggingStatus(traceStatus, methodName));

		return traceStatus;
	}

	private void end(String methodName, TraceStatus status) {
		complete(status, methodName, null);
	}

	private void complete(TraceStatus traceStatus, String methodName, Exception e) {
		long totalTimeMillis = System.currentTimeMillis() - traceStatus.getStartMillis();
		String message = loggingStatus(traceStatus, methodName) + " time=" + totalTimeMillis + "ms";
		TraceStatus nextStatus = TraceStatus.completeCurrLevel(traceStatus);
		threadLocalService.setCurrTrace(nextStatus);

		if (e != null) {
			message += (" ex=" + e.toString() + ": 예외 발생!");
		}

		logger.info(message);
	}

	private void exception(TraceStatus status, String methodName, Exception e) {
		complete(status, methodName, e);
	}

	private String loggingStatus(TraceStatus status, String methodName) {
		return "[" + status.getTraceId() + "] " + createLogLevelString(status.getTraceLevel()) + methodName;

	}

	private String createLogLevelString(int level) {
		String message = "";
		for (int i = 0; i < level; i++) {
			message += "\t|";
		}
		return message;
	}
}
