package GameComponents;

import java.util.ArrayList;

public class Block {
    int x;
    int y;
    int value;
    ArrayList<ConcatBlocksObserver> observers = new ArrayList<>();
    public Block(int x, int y, int value, Map2048 map, PointsCounter counter) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.observers.add(counter);
        this.observers.add(map);
    }

    public int getValue() {
        return value;
    }

    public void notifyConcat(Block block2) {
        for(ConcatBlocksObserver observer : observers) {
            observer.concatBlocks(this, block2);
        }
    }
}
