package quickstart;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

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
        disruptor.handleEventsWith(new OrderEventFirstHandler(), new OrderEventSecondHandler());

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
        BatchEventProcessor<OrderEvent> eventProcessor = new BatchEventProcessor<>();
        eventProcessor.getSequence().get();

    }
}
