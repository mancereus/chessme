package de.db12.game.chessit.shared.model;

public interface Place {

    boolean add(Stone stone);

    void remove(Stone stone);

    Stone getStone();

}
