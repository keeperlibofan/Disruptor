package quickstart;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import high.muliti.OrderEvent;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(16);

        OrderEventFactory factory = new OrderEventFactory();
        Disruptor<OrderEvent> disruptor = new Disruptor<>(
                factory, 1024 * 1024, Executors.newFixedThreadPool(16), ProducerType.SINGLE, new BlockingWaitStrategy());

        EventHandlerGroup<OrderEvent> handlerGroup = disruptor.handleEventsWith(new OrderEventFirstHandler());

        // 同时放入多个EventHandler，多消费者模型
        // 串行操作
        disruptor.handleEventsWith(new OrderEventFirstHandler()).handleEventsWith(new OrderEventSecondHandler());

        // 并行操作
        disruptor.handleEventsWith(new OrderEventSecondHandler());
        disruptor.handleEventsWith(new OrderEventFirstHandler());

        // 多边形操作 -- 菱形操作(一)
        disruptor.handleEventsWith(new OrderEventFirstHandler()).handleEventsWith(new OrderEventFirstHandler(), new OrderEventSecondHandler()).handleEventsWith(new OrderEventFirstHandler());

        // 菱形操作(二)
        disruptor.after(new OrderEventFirstHandler());

        // 六边形操作
        OrderEventFirstHandler h1a = new OrderEventFirstHandler();
        OrderEventSecondHandler h2a = new OrderEventSecondHandler();
        OrderEventFirstHandler h1b = new OrderEventFirstHandler();
        OrderEventSecondHandler h2b = new OrderEventSecondHandler();
        OrderEventFirstHandler finalHandler = new OrderEventFirstHandler();

        disruptor.handleEventsWith(h1a, h1b);
        disruptor.after(h1a).handleEventsWith(h2a);
        disruptor.after(h2a).handleEventsWith(h2b);
        disruptor.after(h2a, h2b).handleEventsWith(finalHandler); // 设置阻塞handler

        //4. 消费者容器获取
        RingBuffer<OrderEvent> ringBuffer = disruptor.start();

        //5. 创建生产者
        OrderEventProducer producer = new OrderEventProducer(ringBuffer);

        CountDownLatch latch = new CountDownLatch(1);

        long begin = System.currentTimeMillis();
        // submit是异步的
        executorService.submit(new OrderEventPublisher(latch, disruptor));

        latch.await();
        executorService.shutdown();


        System.out.println("总耗时：" + (System.currentTimeMillis() - begin) + "ms");

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long i = 0; i < 100; i++) {
            // 从ringBuffer里面获取一个有用的序号
            bb.putLong(0, i);
            producer.sendData(bb);
        }
        // BatchEventProcessor<OrderEvent> eventProcessor = new BatchEventProcessor<>();
        // eventProcessor.getSequence().get();

    }
}
