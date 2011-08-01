package de.db12.game.chessit.shared.game;

import java.util.List;

public interface Game {
	void init();

	void start();

//	void getRound();

	boolean isFinished();

	void finish();

	List<Player> getSpieler();

}
