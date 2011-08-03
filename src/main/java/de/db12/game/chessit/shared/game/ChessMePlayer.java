package de.db12.game.chessit.shared.game;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;
import de.db12.game.chessit.shared.game.ChessMeGame.Type;

public class ChessMePlayer implements Player {
	private final static Random random = new Random();

	private final Color color;
	private final List<Stone> hand = Lists.newArrayList();
	private final List<Stone> trash = Lists.newArrayList();
	private List<Stone> stack = Lists.newArrayList();
	private final ChessMeBoard board;

	public ChessMePlayer(Color color, ChessMeBoard board) {
		this.color = color;
		this.board = board;
		stack = Stone.getAllStones(color);
	}

	private BoardField getFieldWithStoneToMove() {
		List<BoardField> fields = board.getFieldsWithStones(color);
		if (fields.isEmpty())
			return null;
		return fields.get(random.nextInt(fields.size()));
	}

	@Override
	public void init() {
		int modYpawn = color == Color.white ? 2 : -1;
		int modYking = color == Color.white ? 3 : -2;
		board.setStone(board.getMiddleX(), board.getMiddleY() + modYking,
				takeStone(Type.king));
		board.setStone(board.getMiddleX() - 1, board.getMiddleY() + modYpawn,
				takeStone(Type.pawn));
		board.setStone(board.getMiddleX(), board.getMiddleY() + modYpawn,
				takeStone(Type.pawn));
		board.setStone(board.getMiddleX() + 1, board.getMiddleY() + modYpawn,
				takeStone(Type.pawn));
		board.setStone(board.getMiddleX() + 2, board.getMiddleY() + modYpawn,
				takeStone(Type.pawn));
	}

	@Override
	public void initRound() {
		while (!stack.isEmpty() && hand.size() < 3) {
			hand.add(stack.remove(random.nextInt(stack.size())));
		}
	}

	@Override
	public void move() {
		BoardField field = getFieldWithStoneToMove();
		Set<BoardField> targets = field.getMovableFields();
		if (targets.isEmpty())
			return;
		BoardField target = targets.iterator().next();
		target.setStone(field.getStone());
		field.setStone(null);

	}

	@Override
	public String showHand() {
		return color.desc() + "(" + stack.size() + "): "
				+ Joiner.on(" ").join(hand).toString();
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
