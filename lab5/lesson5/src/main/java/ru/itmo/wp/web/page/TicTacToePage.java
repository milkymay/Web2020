package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TicTacToePage {
    private void action(Map<String, Object> view, HttpServletRequest request) {
        if (request.getSession().getAttribute("state") == null) {
            newGame(view, request);
        }
        view.put("state", request.getSession().getAttribute("state"));
    }

    private void newGame(Map<String, Object> view, HttpServletRequest request) {
        State curState = new State();
        view.put("state", curState);
        request.getSession().setAttribute("state", curState);
        throw new RedirectException(request.getRequestURI());
    }

    private void onMove(Map<String, Object> view, HttpServletRequest request) {
        State curState = (State) request.getSession().getAttribute("state");
        if (curState.phase == State.Phase.RUNNING) {
            int x = -1, y = -1;
            for (String name : request.getParameterMap().keySet()) {
                if (name.startsWith("cell")) {
                    y = name.charAt(name.length() - 1) - '0';
                    x = name.charAt(name.length() - 2) - '0';
                }
            }
            curState.makeMove(x, y);
            request.getSession().setAttribute("state", curState);
        }
        view.put("state", curState);
        throw new RedirectException(request.getRequestURI());
    }

    public static class State {
        private final int size = 3;
        private int cnt = 0;
        private final Cell[][] cells;
        private Phase phase;
        private boolean crossesMove;

        public State() {
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

        private enum Cell {
            X, O
        }

        private enum Phase {
            WON_O, WON_X, DRAW, RUNNING
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
}
