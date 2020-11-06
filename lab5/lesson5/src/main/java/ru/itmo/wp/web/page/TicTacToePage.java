package ru.itmo.wp.web.page;

import ru.itmo.wp.model.TicTacToe.*;
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
        if (curState.board.getPhase() == Phase.RUNNING) {
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
        private Board board;
        private final int size = 3;

        public State() {
            this.board = new Board(size);
        }

        public void makeMove(int x, int y) {
            board.makeMove(x, y);
        }

        public int getSize() {
            return size;
        }

        public int getCnt() {
            return board.getCnt();
        }

        public Cell[][] getCells() {
            return board.getCells();
        }

        public Phase getPhase() {
            return board.getPhase();
        }

        public boolean getCrossesMove() {
            return board.getCrossesMove();
        }
    }
}
