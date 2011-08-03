package de.db12.game.chessit.shared.game;

import java.util.List;

import com.google.common.collect.Lists;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;
import de.db12.game.chessit.shared.game.ChessMeGame.Type;

public class ChessMePlayer implements Player {

	private final Color color;
	private final Iterable<Stone> hand = Lists.newArrayList();
	private final Iterable<Stone> trash = Lists.newArrayList();
	private List<Stone> stack = Lists.newArrayList();
	private final ChessMeBoard board;

	public ChessMePlayer(Color color, ChessMeBoard board) {
		this.color = color;
		this.board = board;
		stack = Stone.getAllStones(color);
	}

	@Override
	public void init() {
		int dirY = -1;
		if (color == Color.white)
			dirY = 1;
		board.setStone(board.getMiddleX(), board.getMiddleY() + dirY * 2,
				takeStone(Type.king));
		board.setStone(board.getMiddleX() - 1, board.getMiddleY() + dirY,
				takeStone(Type.pawn));
		board.setStone(board.getMiddleX(), board.getMiddleY() + dirY,
				takeStone(Type.pawn));
		board.setStone(board.getMiddleX() + 1, board.getMiddleY() + dirY,
				takeStone(Type.pawn));
		board.setStone(board.getMiddleX() + 2, board.getMiddleY() + dirY,
				takeStone(Type.pawn));
	}

	@Override
	public void initRound(int round, int minVal) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public Stone takeStone(Type type) {
		for (Stone stone : stack) {
			if (stone.type == type) {
				stack.remove(stone);
				return stone;
			}
		}
		return null;
	}
}
