package de.db12.game.chessit.shared.game;

import java.util.List;

import de.db12.game.chessit.shared.game.ChessMeGame.Stone;

public class ChessMeBoard implements Board {

	int boardsize = 10;
	BoardField[] fields;

	private BoardField getField(int x, int y) {
		return fields[x * boardsize + y];

	}

	@Override
	public List<BoardField> getFreeFields() {
		return null;
	}

	public int getMiddleX() {
		return boardsize / 2;
	}

	public int getMiddleY() {
		return boardsize / 2;
	}

	@Override
	public void init() {
		fields = new BoardField[boardsize * boardsize];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new BoardField(this, i / boardsize, i % boardsize);
		}
	}

	public void setStone(int x, int y, Stone takeStone) {
		getField(x, y).setStone(takeStone);
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < fields.length; i++) {
			if (i % boardsize == 0)
				buf.append("\n");
			buf.append(fields[i].toString() + " ");
		}
		return buf.toString();
	}
}
