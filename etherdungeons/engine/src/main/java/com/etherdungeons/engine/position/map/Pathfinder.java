package com.etherdungeons.engine.position.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Philipp
 */
public class Pathfinder {
    private final GameMap map;
    private final int[] cost, parent;
    private final List<Integer> open;
    private final Set<Integer> closed;

    public Pathfinder(GameMap map) {
        this.map = map;
        int size = map.getWidth() * map.getHeight();
        cost = new int[size];
        parent = new int[size];
        open = new ArrayList<>(size);
        closed = new HashSet<>(size);
    }
    
    public List<Integer> findPath(int from, int to) {
        Arrays.fill(cost, Integer.MAX_VALUE);
        Arrays.fill(parent, Integer.MIN_VALUE);//not necessary
        cost[from] = 0;
        parent[from] = -1;
        open.add(from);
        
        int current = from;
        while(!open.isEmpty()) {
            current = popBest(to);
            if(current == to) {
                open.clear();
                break;
            }
            
            int x = map.x(current);
            int y = map.y(current);
            for (int i = 0; i < 4; i++) {
                int dx = x + (i - 1) % 2;
                int dy = y + (i - 2) % 2;
                if(map.inBounds(dx, dy)) {
                    int neighbor = map.index(dx, dy);
                    
                    if(map.isEmpty(neighbor)) {
                        int nCost = cost[current] + 1;
                        if(nCost < cost[neighbor]) {
                            cost[neighbor] = nCost;
                            parent[neighbor] = current;
                            if(!closed.contains(neighbor) && !open.contains(neighbor)) {
                                open.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        closed.clear();
        
        if(current != to) {
            return null;//no path found
        }
        
        List<Integer> result = new ArrayList<>();
        while(current != from) {
            result.add(0, current);
            current = parent[current];
        }
        return result;
    }
    
    private int popBest(int to) {
        int bestIndex = -1;
        int bestValue = Integer.MAX_VALUE;
        for (int i = 0; i < open.size(); i++) {
            int current = open.get(i);
            int value = cost[current] + map.diagonalDistance(current, to);
            if(value < bestValue) {
                bestIndex = i;
                bestValue = value;
            }
        }
        int best = open.remove(bestIndex);
        closed.add(best);
        return best;
    }
}
