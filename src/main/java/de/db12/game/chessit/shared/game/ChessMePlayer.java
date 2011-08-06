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

	private List<BoardField> getFieldWithStones() {
		List<BoardField> fields = board.getFieldsWithStones(color);
		if (fields.isEmpty())
			return Lists.newArrayList();
		return fields;
	}

	@Override
	public void init() {
		int modRowpawn = color == Color.white ? 2 : -1;
		int modRowking = color == Color.white ? 3 : -2;
		board.setStone(board.getMiddleRow() + modRowking, board.getMiddleCol(), takeStone(Type.king));
		board.setStone(board.getMiddleRow() + modRowpawn, board.getMiddleCol() - 1, takeStone(Type.pawn));
		board.setStone(board.getMiddleRow() + modRowpawn, board.getMiddleCol(), takeStone(Type.pawn));
		board.setStone(board.getMiddleRow() + modRowpawn, board.getMiddleCol() + 1, takeStone(Type.pawn));
		board.setStone(board.getMiddleRow() + modRowpawn, board.getMiddleCol() + 2, takeStone(Type.pawn));
	}

	@Override
	public void initRound() {
		while (!stack.isEmpty() && hand.size() < 3) {
			hand.add(stack.remove(random.nextInt(stack.size())));
		}
	}

	@Override
	public void move() {
		List<BoardField> fields = getFieldWithStones();
		for (BoardField source : fields) {
			Set<BoardField> targets = board.getMovableFields(this, source);
			if (targets.isEmpty())
				continue;
			BoardField target = targets.iterator().next();
			moveFromTo(source, target);
			break;
		}

	}

	private void moveFromTo(BoardField field, BoardField target) {
		if (target.isEmpty())
			board.setComment("move " + field.getStone().toString());
		else {
			board.setComment(field.getStone().toString() + " kills " + target.getStone().toString());
			checkWin(target.getStone());
		}
		target.setStone(field.getStone());
		field.setStone(null);
	}

	private void checkWin(Stone stone) {
		if (stone.type == Type.king) {
			board.getGame().setFinished();
		}
	}

	@Override
	public String showHand() {
		return color.desc() + "(" + stack.size() + "): " + Joiner.on(" ").join(hand).toString();
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

	@Override
	public Color getColor() {
		return color;
	}
}
