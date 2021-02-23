package GameComponents;

public class PointsCounter implements ConcatBlocksObserver {
    private int counter;

    public PointsCounter() {
        this.counter = 0;
    }
    @Override
    public void concatBlocks(Block block1, Block block2) {
        this.counter  += block1.getValue() + block2.getValue();
    }

    public int getScore() {
        return this.counter;
    }
}
