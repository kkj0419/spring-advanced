package com.example.advanced.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.advanced.trace.TraceId;
import com.example.advanced.trace.TraceStatus;

@Component
@Aspect
public class LoggingAspect {

	private final Logger logger = LoggerFactory.getLogger(getClass());

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

			end(status);
			logger.info("request end == " );
		} catch (Exception e) {
			exception(status, e);
		}
	}

	private TraceStatus begin(String methodName){
		TraceStatus traceStatus = new TraceStatus(TraceId.createTraceId(), System.currentTimeMillis());
		logger.info(loggingStatus(traceStatus, methodName));

		return traceStatus;
	}

	private void end(TraceStatus status) {
		complete(status, null);
	}
	
	private void complete(TraceStatus traceStatus, Exception e) {
		traceStatus.completeCurrLevel();
		long totalTimeMillis = System.currentTimeMillis() - traceStatus.getStartMillis();
		String message = loggingStatus(traceStatus, "method") + " time=" + totalTimeMillis + "ms";

		if (e != null) {
			message += (" ex=" + e.toString() + ": 예외 발생!");
		}

		logger.info(message);
	}

	private void exception(TraceStatus status, Exception e) {
		complete(status, e);
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
