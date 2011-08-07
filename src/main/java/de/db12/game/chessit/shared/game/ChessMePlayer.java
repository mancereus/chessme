package de.db12.game.chessit.shared.game;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;
import de.db12.game.chessit.shared.game.ChessMeGame.Type;

public class ChessMePlayer implements Player {
	private final static Random random = new Random();

	private final Color color;
	private final List<Stone> hand = Lists.newArrayList();
	private List<Stone> stack = Lists.newArrayList();
	private final ChessMeBoard board;

	public ChessMePlayer(Color color, ChessMeBoard board) {
		this.color = color;
		this.board = board;
		stack = Stone.getAllStones(color);
	}

	private List<Field> getFieldWithStones() {
		List<Field> fields = board.getFieldsWithStones(color);
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
		List<Field> fields = getFieldWithStones();
		Set<Move> moves = new TreeSet<Move>();
		for (Field source : fields) {
			moves.addAll(board.getMoves(this, source));
		}
		if (moves.isEmpty())
			return;
		Move move = moves.iterator().next();
		moveFromTo(move);
		for (Stone stone : hand) {
			if (insertStone(stone))
				break;
		}

	}

	private boolean insertStone(Stone stone) {
		Set<Field> inserts = board.getInsertFields(stone);
		if (inserts.isEmpty())
			return false;
		Field next = inserts.iterator().next();
		next.setStone(stone);
		hand.remove(stone);
		return true;
	}

	private void moveFromTo(Move move) {
		if (move.getTo().isEmpty())
			board.setComment("move " + move.getFrom().getStone().toString() + "(" + move.getValue() + ")");
		else {
			board.setComment(move.getFrom().getStone().toString() + " kills " + move.getTo().getStone().toString()
					+ "(" + move.getValue() + ")");
			checkWin(move.getTo().getStone());
		}
		board.getTrash().add(move.getTo().getStone());
		move.getTo().setStone(move.getFrom().getStone());
		move.getFrom().setStone(null);
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

	@Override
	public List<Stone> getHand() {
		return hand;
	}
}
