package com.bbl.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * Once we have the event defined we need to create a consumer that will handle these events. 
 * In our case all we want to do is print the value out the the console.
 * @author: Kevin
 */
class LongEventHandler implements EventHandler<LongEvent>,WorkHandler<LongEvent> {

	/**
	 * event:发布到RingBuffer中的事件
	 * sequence:当前正在处理的事件序列号
	 * endOfBatch:是否为Ringbuffer的最后一个
	 */
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
		fun1(event);
		Thread.sleep(1000);
		System.out.println(System.currentTimeMillis()+"["+Thread.currentThread().getName()+"]LongEventHandler.EventHandler consum -> " + event + ",耗时 -> "+(System.currentTimeMillis()-LongEventMain.startTime));
	}

	@Override
	public void onEvent(LongEvent event) throws Exception {

		fun1(event);
		Thread.sleep(1000);
		System.out.println(System.currentTimeMillis()+"["+Thread.currentThread().getName()+"]LongEventHandler.WorkHandler consum -> " + event + ",耗时 -> "+(System.currentTimeMillis()-LongEventMain.startTime));
	}

	public void fun1(LongEvent event){
	}

}
