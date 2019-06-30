package quickstart;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class OrderEventSecondHandler implements EventHandler<OrderEvent>, WorkHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);

    }

    @Override
    public void onEvent(OrderEvent event) throws Exception {
        event.setName("H1");
        Thread.sleep(1000);
    }
}
