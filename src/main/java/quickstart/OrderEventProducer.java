package quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class OrderEventProducer {

    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer byteBuffer) {
        long seq = ringBuffer.next();
        // 根据这个序号，找到具体的"OrderEvent"元素
        try {
            OrderEvent event = ringBuffer.get(seq);
            event.setValue(byteBuffer.getLong(0));
        } finally {
            ringBuffer.publish(seq);
        }
    }
}
