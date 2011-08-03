package de.db12.game.chessit.shared.game;

public interface Game {
	void finish();

	void init();

	void initRound();

	boolean isFinished();

	void start();

}
