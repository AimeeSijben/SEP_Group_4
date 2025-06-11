package mysimulation;

public class Position {
    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void add(Position other) {
        this.row += other.row;
        this.col += other.col;

    }
}
