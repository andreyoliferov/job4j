package ru.job4j.tictactoe;

public class Logic3T {
    private final Figure3T[][] table;

    public Logic3T(Figure3T[][] table) {
        this.table = table;
    }

    public boolean isWinnerX() {
        return false;
    }

    public boolean isWinnerO() {
        return false;
    }

    public boolean hasGap() {
        boolean hasGap = false;
        for (int out = 0; out < 3; out++) {
            for (int in = 0; in < 3; in++) {
                if(!table[out][in].hasMarkO() && !table[out][in].hasMarkX()) {
                    hasGap = true;
                    break;
                }
            }
        }
        return hasGap;
    }
}
