package quickstart;

import com.lmax.disruptor.AbstractSequencer;
import com.lmax.disruptor.InsufficientCapacityException;
import com.lmax.disruptor.WaitStrategy;

public class MySingleProducerSequencer extends AbstractSequencer {

    /**
     * Create with the specified buffer size and wait strategy.
     *
     * @param bufferSize   The total number of entries, must be a positive power of 2.
     * @param waitStrategy
     */
    public MySingleProducerSequencer(int bufferSize, WaitStrategy waitStrategy) {
        super(bufferSize, waitStrategy);
    }

    @Override
    public boolean hasAvailableCapacity(int requiredCapacity) {
        return false;
    }

    @Override
    public long next() {
        return 0;
    }

    @Override
    public long next(int n) {
        return 0;
    }

    @Override
    public long tryNext() throws InsufficientCapacityException {
        return 0;
    }

    @Override
    public long tryNext(int n) throws InsufficientCapacityException {
        return 0;
    }

    @Override
    public long remainingCapacity() {
        return 0;
    }

    @Override
    public void claim(long sequence) {

    }

    @Override
    public void publish(long sequence) {

    }

    @Override
    public void publish(long lo, long hi) {

    }

    @Override
    public boolean isAvailable(long sequence) {
        return false;
    }

    @Override
    public long getHighestPublishedSequence(long sequence, long availableSequence) {
        return 0;
    }
}
