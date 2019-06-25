package quickstart;

import com.lmax.disruptor.SingleProducerSequencer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.TimeoutBlockingWaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;

import java.util.concurrent.atomic.AtomicLong;

public class MySequencer {
    private AtomicLong putSequence = new AtomicLong();

    private AtomicLong offerSequence = new AtomicLong();

    final int bufferSize;

    public MySequencer(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public long next() {
        long result;
        long current;
        long next;
        do  {
            result = putSequence.incrementAndGet();

        } while (putSequence.get() - offerSequence.get() > bufferSize);
        return result;
    }
}
