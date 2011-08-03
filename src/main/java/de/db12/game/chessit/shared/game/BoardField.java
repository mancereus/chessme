package de.db12.game.chessit.shared.game;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.db12.game.chessit.shared.game.ChessMeGame.Stone;

public class BoardField {
	private static final Logger log = LoggerFactory.getLogger("cf");

	private Stone stone;
	private ChessMeBoard board;

	private int y;

	private int x;

	public BoardField(ChessMeBoard board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
	}

	public Board getBoard() {
		return board;
	}

	public Set<BoardField> getMovableFields() {
		Set<BoardField> targets = board.getFreeFields();
		if (isEmpty())
			return targets;
		List<BoardField> moves = getStone().type.getFields(board, this,
				getStone().color.dir());
		targets.retainAll(moves);
		return targets;
	}

	public Stone getStone() {
		return stone;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isEmpty() {
		return stone == null;
	}

	public void setBoard(ChessMeBoard board) {
		this.board = board;
	}

	public void setStone(Stone stone) {
		this.stone = stone;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return isEmpty() ? ".." : getStone().toString();
	}
}
