package quickstart;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

// 消费类
public class OrderEventHandler implements EventHandler<OrderEvent>, WorkHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event.getValue());
    }

    @Override
    public void onEvent(OrderEvent event) throws Exception {
        System.out.println(event.getValue());
    }
}
