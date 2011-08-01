package de.db12.game.chessit.shared.game;


public interface Player {
	public void move(Board board);

	void initRound(int round, int minVal);
}
