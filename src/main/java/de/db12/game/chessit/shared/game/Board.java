package de.db12.game.chessit.shared.game;

import java.util.Set;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;

public interface Board {

	Set<BoardField> getReachableFields(Color color);

	void init();

	void initRound();

}
