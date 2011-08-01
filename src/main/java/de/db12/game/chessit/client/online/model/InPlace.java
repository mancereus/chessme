package de.db12.game.chessit.client.online.model;

public class InPlace implements Place {
    private Stone stone;
    private int pos;

    public InPlace(int pos, Stone stone) {
        this.pos = pos;
        this.stone = stone;
    }

    public InPlace(Stone stone) {
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

    public int getPos() {
        return pos;
    }

}
