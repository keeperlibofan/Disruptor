package quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.MultiProducerSequencer;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import java.nio.ByteBuffer;

public class Main {
    public static void main(String[] args) {
        Disruptor<OrderEvent> disruptor = new Disruptor<OrderEvent>(
                ,,,new BlockingWaitStrategy());

        disruptor.handleEventsWith(new OrderEventHandler());
        //4. 消费者容器获取
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        //5. 创建生产者
        OrderEventProducer producer = new OrderEventProducer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long i = 0; i < 100; i++) {
            // 从ringBuffer里面获取一个有用的序号
            bb.putLong(0, i);
            producer.sendData(bb);
        }

        MultiProducerSequencer
    }
}
