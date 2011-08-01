package de.db12.game.chessit.shared.game;

import de.db12.game.chessit.shared.game.ChessMeGame.Card;
import de.db12.game.chessit.shared.game.ChessMeGame.Color;


public class ChessMePlayer implements Player {

	private Color color;
	private Iterable<Card> hand;
	private Iterable<Card> trash;
	private Iterable<Card> stack;
	public ChessMePlayer(Color color) {
		this.color = color;
		stack = Card.getAllCards(color);
	}

	@Override
	public void move(Board board) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initRound(int round, int minVal) {
		// TODO Auto-generated method stub

	}

}
