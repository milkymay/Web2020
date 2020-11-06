package ru.itmo.wp.model.TicTacToe;

public class Board {
        private int cnt = 0;
        private final int size;
        private final Cell[][] cells;
        private Phase phase;
        private boolean crossesMove;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.phase = Phase.RUNNING;
        this.crossesMove = true;
    }

    public void makeMove(int x, int y) {
        cnt++;
        if (cells[x][y] == null) {
            cells[x][y] = lastCell();
            stateCheck();
            crossesMove = !crossesMove;
        }
    }

    private void stateCheck() {
        if (lineCheck()) {
            phase = crossesMove ? Phase.WON_X : Phase.WON_O;
        } else if (cnt == size * size) {
            phase = Phase.DRAW;
        }
    }

    private boolean lineCheck() {
        for (int i = 0; i < 3; i++) {
            if (cells[i][0] != null && cells[i][0] == cells[i][1] && cells[i][1] == cells[i][2] ||
                    cells[0][i] != null && cells[0][i] == cells[1][i] && cells[1][i] == cells[2][i]) {
                return true;
            }
        }
        return cells[0][0] != null && cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2] ||
                cells[0][2] != null && cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0];
    }

    private Cell lastCell() {
        return crossesMove ? Cell.X : Cell.O;
    }

    public int getSize() {
        return size;
    }

    public int getCnt() {
        return cnt;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Phase getPhase() {
        return phase;
    }

    public boolean getCrossesMove() {
        return crossesMove;
    }
}
