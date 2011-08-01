package de.db12.game.chessit.shared.model;

import de.db12.game.chessit.client.online.BoardPresenter.Player;

public class Stone {
    public enum Type {
        bpawn, bking, bqueen, bbishop, bknight, brook, wpawn, wking, wqueen, wbishop, wknight, wrook, empty
    }

    private final Type type;
    private final Player player;

    public Stone(Type type, Player player) {
        this.type = type;
        this.player = player;
    }

    public Type getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }
}
