package GameComponents;

import java.util.ArrayList;
import java.util.Random;

public class Map2048 implements ConcatBlocksObserver{
    final int LEFT = 1;
    final int RIGHT = 2;
    final int TOP = 1;
    final int BOTTOM = 2;
    Block[][] map4x4 = new Block[4][4];
    PointsCounter counter;
    public int maxValue = 2;
    public ArrayList<Integer> maxValues = new ArrayList<>();
    public Map2048(PointsCounter counter) {
        maxValues.add(2);
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                this.map4x4[i][j] = null;
        Random r = new Random();
        int x1 = r.nextInt(4);
        int y1 = r.nextInt(4);
        int x2 = x1, y2 = y1;
        while(x2 == x1 && y2 == y1) {
            x2 = r.nextInt(4);
            y2 = r.nextInt(4);
        }
        this.counter = counter;
        map4x4[x1][y1] = new Block(x1, y1, 2, this, this.counter);
        map4x4[x2][y2] = new Block(x2, y2, 2, this, this.counter);

    }
    public void moveRows(int direct) {
        boolean isMove = false;
        for(int i = 0; i < 4; i++) {
            if(direct == TOP) {
                ArrayList<Integer> blocksMove = new ArrayList<>();
                int[] vals = new int[4];
                for(int k = 0; k < 4; k++) {
                    if(map4x4[i][k] != null) blocksMove.add(k);
                }
                for(Integer val: blocksMove) {
                    int g = val - 1;
                    while(g >= 0) {
                        if(map4x4[i][g] == null) g--;
                        else if(map4x4[i][g].value == map4x4[i][val].value && vals[g] == 1) {
                            g--;
                            break;
                        }
                        else break;
                    }
                    g = g+1;
                    if(vals[g] == 1) {
                        map4x4[i][g].notifyConcat(map4x4[i][val]);
                        vals[g] = 2;
                        isMove = true;
                    }
                    else if(vals[g] == 0){
                        moveBlock(map4x4[i][val], i, g);
                        vals[g]++;
                        if(val != g)
                            isMove = true;
                    }
                }
            }
            if(direct == BOTTOM) {
                ArrayList<Integer> blocksMove = new ArrayList<>();
                int[] vals = new int[4];
                for(int k = 3; k >= 0; k--) {
                    if(map4x4[i][k] != null) blocksMove.add(k);
                }
                for(Integer val: blocksMove) {
                    int g = val + 1;
                    while(g <= 3) {
                        if(map4x4[i][g] == null) g++;
                        else if(map4x4[i][g].value == map4x4[i][val].value && vals[g] == 1) {
                            g++;
                            break;
                        }
                        else break;
                    }
                    g = g-1;
                    if(vals[g] == 1) {
                        map4x4[i][g].notifyConcat(map4x4[i][val]);
                        vals[g] = 2;
                        isMove = true;
                    }
                    else if(vals[g] == 0){
                        if(val != g)
                            isMove = true;
                        moveBlock(map4x4[i][val], i, g);
                        vals[g]++;
                    }
                }
            }
        }
        if(isMove)
            addRandomBlock();
    }

    public void moveCells(int direct) {
        boolean isMove = false;
        for(int i = 0; i < 4; i++) {
            if(direct == LEFT) {
                ArrayList<Integer> blocksMove = new ArrayList<>();
                int[] vals = new int[4];
                for(int k = 0; k < 4; k++) {
                    if(map4x4[k][i] != null) blocksMove.add(k);
                }
                for(Integer val: blocksMove) {
                    int g = val - 1;
                    while(g >= 0) {
                        if(map4x4[g][i] == null) g--;
                        else if(map4x4[g][i].value == map4x4[val][i].value && vals[g] == 1) {
                            g--;
                            break;
                        }
                        else break;
                    }
                    g = g+1;
                    if(vals[g] == 1) {
                        map4x4[g][i].notifyConcat(map4x4[val][i]);
                        vals[g] = 2;
                        isMove = true;
                    }
                    else if (vals[g] == 0) {
                        moveBlock(map4x4[val][i], g, i);
                        vals[g]++;
                        if(val != g)
                            isMove = true;
                    }
                }
            }
            if(direct == RIGHT) {
                ArrayList<Integer> blocksMove = new ArrayList<>();
                int[] vals = new int[4];
                for(int k = 3; k >= 0; k--) {
                    if(map4x4[k][i] != null) blocksMove.add(k);
                }
                for(Integer val: blocksMove) {
                    int g = val + 1;
                    while(g <= 3) {
                        if(map4x4[g][i] == null) g++;
                        else if(map4x4[g][i].value == map4x4[val][i].value && vals[g] == 1) {
                            g++;
                            break;
                        }
                        else break;
                    }
                    g = g-1;
                    if(vals[g] == 1) {
                        map4x4[g][i].notifyConcat(map4x4[val][i]);
                        vals[g] = 2;
                        isMove = true;
                    }
                    else if(vals[g] == 0){
                        moveBlock(map4x4[val][i], g, i);
                        vals[g]++;
                        if(val != g)
                            isMove = true;
                    }
                }
            }
        }
        if(isMove)
            addRandomBlock();
    }

    private void addRandomBlock() {
        boolean isEmpty = false;
        int[][] values = getValues();
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(values[i][j] == 0)
                    isEmpty = true;
            }
        }
        if(isEmpty) {
            Random r = new Random();
            int x = r.nextInt(4);
            int y = r.nextInt(4);
            while (map4x4[x][y] != null) {
                x = r.nextInt(4);
                y = r.nextInt(4);
            }
            int value = r.nextInt(maxValues.size());
            System.out.println(value);
            value = maxValues.get(value);
            Block block = new Block(x, y, value, this, this.counter);
            map4x4[x][y] = block;
        }
    }

    public void moveBlock(Block block, int x, int y) {
        this.map4x4[block.x][block.y] = null;
        this.map4x4[x][y] = block;
        block.x = x;
        block.y = y;
    }
    @Override
    public void concatBlocks(Block block1, Block block2) {
        if(block1.value*2 > maxValue && maxValue < 8) {
            maxValue = block1.value * 2;
            maxValues.add(maxValue);
        }
        this.map4x4[block1.x][block1.y].value = block1.value * 2;
        this.map4x4[block2.x][block2.y] = null;

    }

    public int[][] getValues() {
        int[][] val = new int[4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if (map4x4[i][j] == null)
                    val[i][j] = 0;
                else
                    val[i][j] = map4x4[i][j].getValue();
            }
        }
        return val;
    }

    public void draw() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(map4x4[i][j] != null)
                    System.out.printf("%8s", map4x4[i][j].value);
                else
                    System.out.printf("%8s", "Empty");
            }
            System.out.print("\n");
        }
        System.out.println("**********************");
    }
}
