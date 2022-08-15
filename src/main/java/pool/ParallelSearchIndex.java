package pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearchIndex<T> extends RecursiveTask<int> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T elementOfSearch;

    private static final int SIZE_FOR_SEQUENTIAL_SEARCH = 10;

    public ParallelSearchIndex(T[] array, int from, int to, T elementOfSearch) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.elementOfSearch = elementOfSearch;
    }

    @Override
    protected int compute() {
        if ((to - from) <= SIZE_FOR_SEQUENTIAL_SEARCH) {
            return sequentialSearch();
        }
        int mid = (to - from) / 2;

        ParallelSearchIndex<T> leftPartOfArray = new ParallelSearchIndex<>(array, from, mid, elementOfSearch);
        ParallelSearchIndex<T> rightPartOfArray = new ParallelSearchIndex<>(array, mid + 1, to, elementOfSearch);

        leftPartOfArray.fork();
        rightPartOfArray.fork();

        return Math.max(leftPartOfArray.join(), rightPartOfArray.join());
    }

    private int sequentialSearch() {
        int rst = -1;
        for (int i = from; i < to; i++) {
            if (elementOfSearch.equals(array[i])) {
                rst = i;
                break;
            }
        }
        return rst;
    }

    public int search(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return (int) forkJoinPool.invoke(new ParallelSearchIndex(array, 0, array.length - 1, value));
    }
}
