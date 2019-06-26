package quickstart;

import com.lmax.disruptor.EventHandler;

public class OrderEventSecondHandler implements EventHandler<OrderEvent> {


    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {

    }
}
