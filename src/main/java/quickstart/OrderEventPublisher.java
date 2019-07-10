package quickstart;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import high.muliti.OrderEvent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class OrderEventPublisher implements Runnable {

    private CountDownLatch latch;
    private Disruptor<OrderEvent> disruptor;

    private static final int PUBLISH_COUNT = 10;

    public OrderEventPublisher(CountDownLatch latch, Disruptor<OrderEvent> disruptor) {
        this.latch = latch;
        this.disruptor = disruptor;
    }

    @Override
    public void run() {
        OrderEventTranslator translator = new OrderEventTranslator();
        for (int i = 0; i < PUBLISH_COUNT; i++) {
            disruptor.publishEvent(translator);
        }

        latch.countDown();
    }
}

class OrderEventTranslator implements EventTranslator<OrderEvent> {
    private Random random = new Random();

    @Override
    public void translateTo(OrderEvent event, long sequence) {
        this.generateOrder(event);
    }

    private void generateOrder(OrderEvent event) {
        event.setValue(random.nextLong() * 9999);
    }
}
