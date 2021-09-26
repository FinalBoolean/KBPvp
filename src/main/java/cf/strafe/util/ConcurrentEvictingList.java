package cf.strafe.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

@Getter
@RequiredArgsConstructor
public final class ConcurrentEvictingList<T> extends ConcurrentLinkedDeque<T> {

    private final int maxSize;

    public ConcurrentEvictingList(final Collection<? extends T> c, final int maxSize) {
        super(c);
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(final T t) {
        if (size() >= getMaxSize()) removeFirst();
        return super.add(t);
    }

    public boolean isFull() {
        return size() >= getMaxSize();
    }
}