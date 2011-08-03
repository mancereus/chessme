package de.db12.game.chessit.shared.game;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;

public class ChessMeBoard implements Board {

	static class View {
		int minx = Integer.MAX_VALUE;

		int miny = Integer.MAX_VALUE;
		int maxx = 0;
		int maxy = 0;

		private final ChessMeBoard board;

		public View(ChessMeBoard board) {
			this.board = board;
			Set<BoardField> freeFields = board.getFreeFields();
			for (BoardField ff : freeFields) {
				if (ff.getX() < minx)
					minx = ff.getX();
				if (ff.getX() > maxx)
					maxx = ff.getX();
				if (ff.getY() < miny)
					miny = ff.getY();
				if (ff.getY() > maxy)
					maxy = ff.getY();
			}
		}

		@Override
		public String toString() {
			StringBuffer buf = new StringBuffer("\n");

			for (int i = 0; i < board.fields.length; i++) {
				if (i % board.boardsize < minx || i / board.boardsize < miny
						|| i / board.boardsize > maxy) {
					continue;
				}
				if (i % board.boardsize > maxx) {
					buf.append("\n");
					continue;
				}
				if (i % board.boardsize == 0)
					buf.append("\n");
				buf.append(board.fields[i].toString() + " ");
			}
			return buf.toString();
		}
	}

	int boardsize = 10;

	protected BoardField[] fields;

	public BoardField getField(int x, int y) {
		return fields[y * boardsize + x];

	}

	public List<BoardField> getFieldsWithStones(Color color) {
		List<BoardField> ret = Lists.newArrayList();
		for (BoardField field : fields) {
			if (!field.isEmpty() && field.getStone().color == color)
				ret.add(field);
		}
		return ret;
	}

	@Override
	public Set<BoardField> getFreeFields() {
		Set<BoardField> ffs = Sets.newHashSet();
		for (BoardField f : fields) {
			if (f.isEmpty())
				continue;
			ffs.add(getField(f.getX(), f.getY()));
			ffs.add(getField(Math.max(0, f.getX() - 1), f.getY()));
			ffs.add(getField(f.getX(), Math.max(0, f.getY() - 1)));
			ffs.add(getField(Math.min(boardsize - 1, f.getX() + 1), f.getY()));
			ffs.add(getField(f.getX(), Math.min(boardsize - 1, f.getY() + 1)));
		}
		return ffs;
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
			fields[i] = new BoardField(this, i % boardsize, i / boardsize);
		}
	}

	@Override
	public void initRound() {
	}

	public void setStone(int x, int y, Stone takeStone) {
		getField(x, y).setStone(takeStone);
	}

	@Override
	public String toString() {
		// StringBuffer buf = new StringBuffer();

		// for (int i = 0; i < fields.length; i++) {
		// if (i % boardsize == 0)
		// buf.append("\n");
		// buf.append(fields[i].toString() + " ");
		// }
		// return buf.toString();
		return new View(this).toString();
	}
}
