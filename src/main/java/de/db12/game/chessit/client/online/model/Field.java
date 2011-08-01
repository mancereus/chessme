package de.db12.game.chessit.client.online.model;

public class Field implements Place {
    private Stone stone;
    private int xpos;
    private int ypos;

    public Field(int x, int y, Stone stone) {
        this.xpos = x;
        this.ypos = y;
        this.stone = stone;
    }

    public Field(Stone stone) {
        this.stone = stone;
    }

    @Override
    public boolean add(Stone stone) {
        if (stone == null)
            return false;
        this.stone = stone;
        return true;

    }

    @Override
    public void remove(Stone stone) {
        this.stone = null;

    }

    public Stone getStone() {
        return stone;

    }

    public int getY() {
        return ypos;
    }

    public int getX() {
        return xpos;
    }

}
