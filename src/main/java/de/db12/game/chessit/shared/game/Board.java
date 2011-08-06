package de.db12.game.chessit.shared.game;

import java.util.Set;

public interface Board {

	void init();

	void initRound();

	Set<Field> updateReachableFields();

}
