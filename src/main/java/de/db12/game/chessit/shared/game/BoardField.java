package de.db12.game.chessit.shared.game;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.thirdparty.guava.common.collect.Sets;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;

public class BoardField {
	private static final Logger log = LoggerFactory.getLogger("cf");

	private Stone stone;
	private ChessMeBoard board;

	private int col;

	private int row;

	private boolean reachable;

	public BoardField(ChessMeBoard board, int x, int y) {
		this.board = board;
		this.row = x;
		this.col = y;
	}

	public Board getBoard() {
		return board;
	}

	public Set<BoardField> getMovableFields(Color color) {
		Set<BoardField> targets = board.getReachableFields();
		if (isEmpty())
			return targets;
		List<BoardField> moves = getStone().type.getFields(board, this, getStone().color.dir());
		Set<BoardField> ret = Sets.newHashSet();
		for (BoardField boardField : targets) {
			if (!boardField.isEmpty() && boardField.getStone().color == color)
				continue;
			if (moves.contains(boardField))
				ret.add(boardField);
		}
		return ret;
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
		BoardField other = (BoardField) obj;
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

}
