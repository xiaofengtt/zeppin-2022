package com.bbl.disruptor;

import com.lmax.disruptor.*;

/**
 * The other advantage of this approach is that the translator code can be pulled into a separate class and easily unit tested independently. 
 * The Disruptor provides a number of different interfaces (EventTranslator, EventTranslatorOneArg, EventTranslatorTwoArg, etc.) that can be implemented to provide translators. 
 * The reason for is to allow for the translators to be represented as static classes or non-capturing lambda (when Java 8 rolls around) as the arguments to the translation method are passed through the call on the Ring Buffer through to the translator.
 * 
 *  根本目的就是屏蔽模式化代码, 否则每一个生产者都需要些这些模式化的代码:
    long sequence = ringBuffer.next();
	try {
		LongEvent event = ringBuffer.get(sequence);
		// Do some work with the event.
	} finally {
		ringBuffer.publish(sequence);
	}
 *

 */
public class LongEventProducerWithTranslator {

	private final RingBuffer<LongEvent> ringBuffer;

	public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	/**
	 * 一个参数的EventTranslator
	 */
	private static final EventTranslatorOneArg<LongEvent, Long> TRANSLATOR_ONE = new EventTranslatorOneArg<LongEvent, Long> () {
		public void translateTo(LongEvent event, long sequence, Long bb) {
			event.set(bb);
			System.out.println("TRANSLATOR_ONE.."+event);
		}
	};
	
	/**
	 * 两个参数的EventTranslator
	 */
	private static final EventTranslatorTwoArg<LongEvent, Long, Long> TRANSLATOR_TWO = new EventTranslatorTwoArg<LongEvent, Long, Long> () {
		public void translateTo(LongEvent event, long sequence, Long arg0, Long arg1) {
			event.set(arg0+arg1);
			System.out.println("TRANSLATOR_TWO.."+event);
		}
	};
	
	/**
	 * 三个参数的EventTranslator
	 */
	private static final EventTranslatorThreeArg<LongEvent, Integer, Long, Long> TRANSLATOR_THREE = new EventTranslatorThreeArg<LongEvent, Integer, Long, Long> () {
		public void translateTo(LongEvent event, long sequence, Integer arg0, Long arg1, Long arg2) {
			event.set(arg0+arg1+arg2);
			System.out.println("TRANSLATOR_THREE.."+event);
		}
	};
	
	/**
	 * 不固定参数的EventTranslator
	 */
	private static final EventTranslatorVararg<LongEvent> TRANSLATOR_VARGS = new EventTranslatorVararg<LongEvent> () {
		public void translateTo(LongEvent event, long sequence, Object... args) {
			event.set((Long)args[0]);
			System.out.println("TRANSLATOR_VARGS("+args+").."+event);
		}
	};
	
	/**
	 * 无参数的EventTranslator
	 */
	private static final EventTranslator<LongEvent> TRANSLATOR_NONE = new EventTranslator<LongEvent> () {
		public void translateTo(LongEvent event, long sequence) {
			event.set(10L);
			System.out.println("TRANSLATOR_NONE.."+event);
		}
	};
	
	public void onData() {
		ringBuffer.publishEvent(TRANSLATOR_NONE);
	}
	public void onData(Long one) {
		ringBuffer.publishEvent(TRANSLATOR_ONE,one);
	}
	public void onData(Long a,Long b) {
		ringBuffer.publishEvent(TRANSLATOR_TWO,a,b);
	}
	public void onData(Integer a,Long b, Long c) {
		ringBuffer.publishEvent(TRANSLATOR_THREE,a,b,c);
	}
	public void onData(Long a,Long b,String c,String d, String e) {
		ringBuffer.publishEvent(TRANSLATOR_VARGS,a,b,c,d,e);
	}
	
//	//独立编写
//	MyTranslatorTwo two = new MyTranslatorTwo();
//	public void publish(Long age,String name) {
//		ringBuffer.publishEvent(two,age,name);
//	}


}




//
//class MyTranslator implements EventTranslator<LongEvent> {
//	public void translateTo(LongEvent event, long sequence) {
//		event.set(10);
//		System.out.println("MyTranslator.."+event);
//	}
//}
//
//class MyTranslatorOne implements EventTranslatorOneArg<LongEvent, Long> {
//	public void translateTo(LongEvent event, long sequence, Long arg0) {
//		event.set(arg0);
//		System.out.println("MyTranslatorOne.."+event);
//	}
//}
//
//class MyTranslatorTwo implements EventTranslatorTwoArg<LongEvent, Long, String> {
//	public void translateTo(LongEvent event, long sequence, Long arg0, String arg1) {
//		System.out.println("MyTranslatorTwo hello " + arg1);
//		event.set(arg0);
//		System.out.println("MyTranslatorTwo.."+event);
//	}
//

//}



















