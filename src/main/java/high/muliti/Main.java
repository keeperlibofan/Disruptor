package high.muliti;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;

public class Main {
    public static void main(String[] args) {
        RingBuffer<OrderEvent> ringBuffer =
                RingBuffer.createMultiProducer(new OrderEventFactory(), 1024*1024, new YieldingWaitStrategy());
    }
}
