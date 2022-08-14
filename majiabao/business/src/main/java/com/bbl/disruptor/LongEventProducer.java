package com.bbl.disruptor;

import com.lmax.disruptor.RingBuffer;


public class LongEventProducer {

	private final RingBuffer<LongEvent> ringBuffer;

	public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
		this.ringBuffer = ringBuffer;
	}

	public void onData(Long vv) {
		//获取下一个序号
		long sequence = ringBuffer.next();
		try {
			//根据下一个序号获取Event
			LongEvent event = ringBuffer.get(sequence);
			// Do some work with the event.
			event.set(vv);
			System.out.println("["+Thread.currentThread().getName()+"] LongEventProducer product -> " + event);
		} finally {
			//发布序号(发布后可以被消费)
			ringBuffer.publish(sequence);
		}
	}
}
