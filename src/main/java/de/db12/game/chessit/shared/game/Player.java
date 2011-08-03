package de.db12.game.chessit.shared.game;

import de.db12.game.chessit.shared.game.ChessMeGame.Stone;
import de.db12.game.chessit.shared.game.ChessMeGame.Type;

public interface Player {
	public void init();

	void initRound(int round, int minVal);

	public void move();

	public Stone takeStone(Type type);
}
