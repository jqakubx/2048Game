package GameComponents;

public class Game2048 {
    public Map2048 map;
    public PointsCounter counter;
    public Game2048() {
        this.counter = new PointsCounter();
        this.map = new Map2048(counter);

    }

    public int[][] getValues() {
        return this.map.getValues();
    }
    public int getScore() { return this.counter.getScore();}

    public boolean checkLose() {
        int[][] values = getValues();
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(values[i][j] == 0)
                    return false;
            }
        }
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                if(values[i][j] == values[i+1][j])
                    return false;
                if(values[j][i] == values[j][i+1])
                    return false;
            }
        }
        return true;
    }

    public boolean checkVictory() {
        int[][] values = getValues();
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(values[i][j] == 2048) return true;
            }
        }
        return false;
    }
}
