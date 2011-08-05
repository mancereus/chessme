package de.db12.game.chessit.shared.game;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;

public class BoardField {
	private static final Logger log = LoggerFactory.getLogger("cf");

	private Stone stone;
	private ChessMeBoard board;

	private int y;

	private int x;

	private boolean reachable;

	public BoardField(ChessMeBoard board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
	}

	public Board getBoard() {
		return board;
	}

	public Set<BoardField> getMovableFields(Color color) {
		Set<BoardField> targets = board.getReachableFields(color);
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
		return isEmpty() ? (isReachable() ? ".." : "  ") : getStone()
				.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoardField other = (BoardField) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public boolean isReachable() {
		return reachable;
	}

	public void setReachable(boolean reachable) {
		this.reachable = reachable;
	}

}
