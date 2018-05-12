package ru.job4j.tictactoe;

public class Logic3T {
    private final Figure3T[][] table;

    public Logic3T(Figure3T[][] table) {
        this.table = table;
    }

    private boolean condition(int out, int in, boolean markX, boolean horizontal) {
        boolean cond;
        if (markX) {
            cond = horizontal ? table[out][in].hasMarkX() : table[in][out].hasMarkX();
        } else {
            cond = horizontal ? table[out][in].hasMarkO() : table[in][out].hasMarkO();
        }
        return cond;
    }

    private boolean isWinner(boolean markX, boolean horizontal) {
        boolean win = false;
        for (int out = 0; out < 3; out++) {
            int count = 0;
            for (int in = 0; in < 3; in++) {
                if (condition(out, in, markX, horizontal)) {
                    count++;
                } else {
                    break;
                }
                if (count == 3) {
                    win = true;
                }
            }
        }
        return win;
    }

    private boolean condition(int i, boolean markX, boolean diagonal) {
        boolean cond;
        if (markX) {
            cond = diagonal ? table[i][i].hasMarkX() : table[i][2 - i].hasMarkX();
        } else {
            cond = diagonal ? table[i][i].hasMarkO() : table[i][2 - i].hasMarkO();
        }
        return cond;
    }

    private boolean isWinnerDiagonal(boolean markX, boolean diagonal) {
        boolean win = false;
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if (condition(i, markX, diagonal)) {
                count++;
            } else {
                break;
            }
            if (count == 3) {
                win = true;
            }
        }
        return win;
    }

    public boolean isWinnerX() {
        return isWinner(true, true)
                || isWinner(true, false)
                || isWinnerDiagonal(true, true)
                || isWinnerDiagonal(true, false);
    }

    public boolean isWinnerO() {
        return isWinner(false, true)
                || isWinner(false, false)
                || isWinnerDiagonal(false, true)
                || isWinnerDiagonal(false, false);
    }

    public boolean hasGap() {
        boolean hasGap = false;
        for (int out = 0; out < 3; out++) {
            for (int in = 0; in < 3; in++) {
                if (!table[out][in].hasMarkO() && !table[out][in].hasMarkX()) {
                    hasGap = true;
                    break;
                }
            }
        }
        return hasGap;
    }
}
