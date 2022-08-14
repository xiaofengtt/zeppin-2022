package com.bbl.disruptor.exception;

import com.bbl.disruptor.LongEvent;
import com.lmax.disruptor.ExceptionHandler;

public class DefaultUserEventExceptionHandler implements ExceptionHandler<LongEvent> {

	public void handleEventException(Throwable ex, long sequence, LongEvent event) {
		System.out.println("DefaultUserEventExceptionHandler.handleEventException->"+event+"->"+ex.getMessage());
	}

	public void handleOnStartException(Throwable ex) {
		System.out.println("DefaultUserEventExceptionHandler.handleOnStartException->"+ex.getMessage());
	}

	public void handleOnShutdownException(Throwable ex) {
		System.out.println("DefaultUserEventExceptionHandler.handleOnShutdownException->"+ex.getMessage());
	}

}
