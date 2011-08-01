package de.db12.game.chessit.shared.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Random;

import de.db12.game.chessit.client.online.BoardPresenter.Player;
import de.db12.game.chessit.client.online.BoardPresenter.State;
import de.db12.game.chessit.shared.model.Stone.Type;

public class BoardModel {

    private final Board board;
    private final List<Stone> wDrop = new ArrayList<Stone>();
    private final List<Stone> bDrop = new ArrayList<Stone>();
    private final List<Stone> bHand = new ArrayList<Stone>();
    private final List<Stone> wHand = new ArrayList<Stone>();

    private State state = State.blackset;

    public BoardModel(int size) {
        getBHand().add(new Stone(Type.bknight, Player.black));
        getBHand().add(new Stone(Type.bknight, Player.black));
        getBHand().add(new Stone(Type.brook, Player.black));
        getBHand().add(new Stone(Type.brook, Player.black));
        getBHand().add(new Stone(Type.bbishop, Player.black));
        getBHand().add(new Stone(Type.bbishop, Player.black));
        getBHand().add(new Stone(Type.bqueen, Player.black));
        getBHand().add(new Stone(Type.bpawn, Player.black));
        getBHand().add(new Stone(Type.bpawn, Player.black));
        getBHand().add(new Stone(Type.bpawn, Player.black));
        shuffle(getBHand());
        getWHand().add(new Stone(Type.wknight, Player.white));
        getWHand().add(new Stone(Type.wknight, Player.white));
        getWHand().add(new Stone(Type.wrook, Player.white));
        getWHand().add(new Stone(Type.wrook, Player.white));
        getWHand().add(new Stone(Type.wbishop, Player.white));
        getWHand().add(new Stone(Type.wbishop, Player.white));
        getWHand().add(new Stone(Type.wqueen, Player.white));
        getWHand().add(new Stone(Type.wpawn, Player.white));
        getWHand().add(new Stone(Type.wpawn, Player.white));
        getWHand().add(new Stone(Type.wpawn, Player.white));
        shuffle(getWHand());

        this.board = new Board(size);
        int middlex = size / 2;
        int middley = size / 2;
        getBoard().addStone(middlex, middley - 2, new Stone(Type.bking, Player.black));
        getBoard().addStone(middlex - 1, middley - 1, new Stone(Type.bpawn, Player.black));
        getBoard().addStone(middlex, middley - 1, new Stone(Type.bpawn, Player.black));
        getBoard().addStone(middlex + 1, middley - 1, new Stone(Type.bpawn, Player.black));

        getBoard().addStone(middlex, middley + 3, new Stone(Type.wking, Player.white));
        getBoard().addStone(middlex - 1, middley + 2, new Stone(Type.wpawn, Player.white));
        getBoard().addStone(middlex, middley + 2, new Stone(Type.wpawn, Player.white));
        getBoard().addStone(middlex + 1, middley + 2, new Stone(Type.wpawn, Player.white));
    }

    private void shuffle(List<Stone> hand) {
        for (int i = 0; i < hand.size(); i++) {
            int pos = Random.nextInt(hand.size());
            int posTo = Random.nextInt(hand.size());
            swap(hand, pos, posTo);

        }

    }

    private void swap(List<Stone> hand, int pos, int posTo) {
        Stone tmp = hand.get(pos);
        hand.set(pos, hand.get(posTo));
        hand.set(posTo, tmp);

    }

    public Board getBoard() {
        return board;
    }

    public List<Stone> getBHand() {
        return bHand;
    }

    public List<Stone> getWHand() {
        return wHand;
    }

    public List<Stone> getbDrop() {
        return bDrop;
    }

    public List<Stone> getwDrop() {
        return wDrop;
    }

    public void moveStone(Place origin, Stone stone, Place target) {
        checkEmpties(origin);
        origin.remove(stone);
        Stone targetstone = target.getStone();
        if (targetstone != null) {
            if (targetstone.getPlayer() == Player.white)
                getwDrop().add(targetstone);
            if (targetstone.getPlayer() == Player.black)
                getbDrop().add(targetstone);
        }
        target.add(stone);

    }

    public void nextState() {
        state = state.next();
    }

    public State getState() {
        return state;
    }

    private void checkEmpties(Place origin) {

    }
}
