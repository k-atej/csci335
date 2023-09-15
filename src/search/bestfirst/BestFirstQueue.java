package search.bestfirst;

import search.SearchNode;
import search.SearchQueue;

import java.util.*;
import java.util.function.ToIntFunction;

public class BestFirstQueue<T> implements SearchQueue<T> {
    PriorityQueue<SearchNode<T>> pq;
    ToIntFunction<T> heuristic;
    // TODO: Implement this class
    // HINT: Use java.util.PriorityQueue. It will really help you.

    private HashMap<T, Integer> visited;

    public BestFirstQueue(ToIntFunction<T> heuristic) {
        this.heuristic = heuristic;
        this.pq = new PriorityQueue<>(Comparator.comparingInt(searchnode -> this.heuristic.applyAsInt(searchnode.getValue())));
        this.visited = new HashMap<>();
    }

    private int Total(SearchNode<T> searchnode){
        return this.heuristic.applyAsInt(searchnode.getValue()) + searchnode.getDepth();
    }

    @Override
    public void enqueue(SearchNode<T> node) {
        // TODO: Your code here (to consider visited nodes and their estimates)
        int Total = Total(node);

        // If the node is already visited and has a lesser or equal estimate, then skip.
        if (visited.containsKey(node.getValue())){
            if (visited.get(node.getValue()) <= Total) {
                return;
            }
        }

        pq.add(node);
        visited.put(node.getValue(), Total);
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
