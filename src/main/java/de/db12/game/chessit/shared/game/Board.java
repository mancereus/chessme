package de.db12.game.chessit.shared.game;

import java.util.Set;

public interface Board {

	Set<BoardField> getFreeFields();

	void init();

	void initRound();

}
