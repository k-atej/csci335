package search.bestfirst;

import search.SearchNode;
import search.SearchQueue;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.ToIntFunction;

public class BestFirstQueue<T> implements SearchQueue<T> {
    PriorityQueue<SearchNode<T>> pq;
    ToIntFunction<T> heuristic;
    // TODO: Implement this class
    // HINT: Use java.util.PriorityQueue. It will really help you.

    private HashSet<T> visited = new HashSet<>();

    public BestFirstQueue(ToIntFunction<T> heuristic) {
        this.heuristic = heuristic;
        this.pq = new PriorityQueue<>(Comparator.comparingInt(searchnode -> this.heuristic.applyAsInt(searchnode.getValue())));
        // i don't think i understand how this comparator really works, but its passing the test
        // i found this comparator notation on geeksforgeeks
    }

    @Override
    public void enqueue(SearchNode<T> node) {
        if (!visited.contains(node.getValue())) {
            pq.add(node);
            visited.add(node.getValue());
        }
        // TODO: Your code here
    }

    @Override
    public Optional<SearchNode<T>> dequeue() {
        if (pq.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(pq.remove());
        }
        // TODO: Your code here
        // TODO: Replace this line; it is here to let the code compile.
    }
}
