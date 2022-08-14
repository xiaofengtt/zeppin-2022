package com.bbl.disruptor;

import com.bbl.disruptor.exception.DefaultUserEventExceptionHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 使用Translator接口,替代Producer,简化代码,降低出错几率
 */
public class LongEventMain {
	public static long startTime;

	public static void main(String[] args) throws InterruptedException {
		/*// 初始化线程池-用户执行Consumer
		Executor executor = Executors.newCachedThreadPool();
		// 初始化EventFactory
		LongEventFactory factory = new LongEventFactory();
		// 初始化RingBuffer的大小,必须是2的指数  长度设置为2的N次方，有利于二进制计算机计算。
		int bufferSize = 1024;
		// 初始化RingBuffer
		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, executor);
		// 指定事件消费者
		disruptor.handleEventsWith(new LongEventHandler());
		// 开启Disruptor,开启所有线程,(此方法只能调用一次,并且所有的EventHandler必须在start之前添加,包括ExceptionHandler)
		disruptor.start();
		// 获取RingBuffer
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();*/



		// 初始化线程池-用户执行Consumer
		// Executor threadFactory = Executors.newCachedThreadPool();
		ThreadFactory threadFactory = Executors.defaultThreadFactory();
		LongEventFactory factory = new LongEventFactory();
		int bufferSize = 1024;
		//等待策略
		BlockingWaitStrategy waitStrategy = new BlockingWaitStrategy(); //阻塞，通用低耗
		//SleepingWaitStrategy waitStrategy = new SleepingWaitStrategy(); //睡眠，和BlockingWaitStrategy类似，适合记录日志
		//YieldingWaitStrategy waitStrategy = new YieldingWaitStrategy();  //慎用
		Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory, bufferSize, threadFactory, ProducerType.MULTI, waitStrategy);//多线程模式   ProducerType.SINGLE
		//指定异常捕获类
		disruptor.setDefaultExceptionHandler(new DefaultUserEventExceptionHandler ());
		// 指定事件消费者
		LongEventHandler[] arr1 = new LongEventHandler[10];   //[10]线程数
		for(int i=0;i<10;i++) {
			arr1[i] = new LongEventHandler ();
		}


		//多线程消费
		disruptor.handleEventsWithWorkerPool(arr1);
		//单线程消费
		//LongEventHandler longEventHandler=new LongEventHandler();
		//disruptor.handleEventsWith(longEventHandler);  //传arr1数组，会导致重复消费


		//依赖链,单个序号的消费流程，可被消费多次
		//串行
		//[ringbuffer] -> LongEventHandler[多线程]
		//disruptor.handleEventsWithWorkerPool(arr1);
		//[ringbuffer] -> LongEventHandler[多线程] -> LongEventHandler[单线程]
		//disruptor.handleEventsWithWorkerPool(arr1).handleEventsWith(new LongEventHandler());
		//[ringbuffer] -> LongEventHandler[多线程] -> LongEventHandler[多线程]
		//disruptor.handleEventsWithWorkerPool(arr1).handleEventsWithWorkerPool(arr1);

		//并行
		//disruptor.handleEventsWithWorkerPool(arr1);
		//disruptor.handleEventsWithWorkerPool(arr1);

		//分组
		//EventHandlerGroup<LongEvent> group1 = disruptor.handleEventsWith(arr1).handleEventsWith(arr1); //串行分组
		//EventHandlerGroup<LongEvent> group2 = disruptor.handleEventsWith(arr1).handleEventsWith(arr1); //串行分组
		//group1.and(group2).handleEventsWith(longEventHandler);  //先并行（and）group1，group2分组，并行完成后在串行longEventHandler
		//group1.and(group2).thenHandleEventsWithWorkerPool(arr1);  //先并行（and）group1，group2分组，并行完成后在串行arr1

		// 开启Disruptor,开启所有线程,(此方法只能调用一次,并且所有的EventHandler必须在start之前添加,包括ExceptionHandler)
		disruptor.start();
		// 获取RingBuffer
		RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
		startTime = System.currentTimeMillis();


		// 生产者,采用固定模式向RingBuffer中添加事件
		//final LongEventProducer producer = new LongEventProducer(ringBuffer);
		//producer.onData(1L);

		//生产者,模式化
		LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
		//producer.publish(100L, "张三");  //独立编写
		producer.onData();
		producer.onData(1L);
		producer.onData(1L,2L);
		producer.onData(1,2L,3L);
		producer.onData(1L,2L,"3","4","5");


		//关闭线程池
		//executor.shutdown();
//		((ExecutorService) threadFactory).shutdown ();
		//关闭Disruptor
		disruptor.shutdown();
	}

}
