package quickstart;

import com.lmax.disruptor.EventFactory;
import high.muliti.OrderEvent;

public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
