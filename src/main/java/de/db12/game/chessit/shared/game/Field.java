package de.db12.game.chessit.shared.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.db12.game.chessit.shared.game.ChessMeGame.Stone;

public class Field {
	private static final Logger log = LoggerFactory.getLogger("cf");

	private Stone stone;
	private ChessMeBoard board;

	private int col;

	private int row;

	private boolean reachable;

	public Field(ChessMeBoard board, int x, int y) {
		this.board = board;
		this.row = x;
		this.col = y;
	}

	public Board getBoard() {
		return board;
	}

	public Stone getStone() {
		return stone;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
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

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

	@Override
	public String toString() {
		return isEmpty() ? (isReachable() ? ".." : "  ") : getStone().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + row;
		result = prime * result + col;
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
		Field other = (Field) obj;
		if (row != other.row)
			return false;
		if (col != other.col)
			return false;
		return true;
	}

	public boolean isReachable() {
		return reachable;
	}

	public void setReachable(boolean reachable) {
		this.reachable = reachable;
	}

	public List<Field> getMoves() {
		if (isEmpty())
			return new ArrayList<Field>();
		return getStone().type.getFields(board, this, getStone().color.dir());
	}
}
