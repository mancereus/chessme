package de.db12.game.chessit.shared.game;

import java.util.List;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;
import de.db12.game.chessit.shared.game.ChessMeGame.Type;

public interface Player {
	public void init();

	void initRound();

	public void move();

	public String showHand();

	public Stone takeStone(Type type);

	public Color getColor();

	public List<Stone> getHand();
}
