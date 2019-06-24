package quickstart;

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
        do  {
            result = putSequence.incrementAndGet();
        } while (putSequence.get() - offerSequence.get() > bufferSize);
        return result;
    }
}
