package de.db12.game.chessit.shared.game;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;

public class ChessMeBoard implements Board {

	int boardsize = 30;

	protected BoardField[][] fields;

	private String comment;

	private Game game;

	private int dec(int val) {
		return Math.max(0, val - 1);
	}

	private int inc(int val) {
		return Math.min(boardsize - 1, val + 1);
	}

	@Override
	public void init() {
		fields = new BoardField[boardsize][boardsize];
		for (int row = 0; row < boardsize; row++) {
			for (int col = 0; col < boardsize; col++) {
				fields[row][col] = new BoardField(this, row, col);
			}
		}
	}

	@Override
	public void initRound() {
	}

	public BoardField getField(int row, int col) {
		return fields[row][col];

	}

	public void addCheckField(Set<BoardField> ff, int row, int col) {
		BoardField field = getField(row, col);
		field.setReachable(true);
		ff.add(field);
	}

	public List<BoardField> getFieldsWithStones(Color color) {
		List<BoardField> ret = Lists.newArrayList();
		for (BoardField[] fieldrow : fields) {
			for (BoardField fieldcol : fieldrow) {
				if (!fieldcol.isEmpty() && fieldcol.getStone().color == color)
					ret.add(fieldcol);
			}
		}
		return ret;
	}

	public int getMiddleRow() {
		return boardsize / 2;
	}

	public int getMiddleCol() {
		return boardsize / 2;
	}

	@Override
	public Set<BoardField> getReachableFields() {
		Set<BoardField> ffs = Sets.newHashSet();
		for (BoardField[] frow : fields) {
			for (BoardField fcol : frow) {
				fcol.setReachable(false);
			}
		}
		for (BoardField[] frow : fields) {
			for (BoardField fcol : frow) {
				if (fcol.isEmpty())
					continue;
				addCheckField(ffs, fcol.getRow(), fcol.getCol());
				addCheckField(ffs, dec(fcol.getRow()), dec(fcol.getCol()));
				addCheckField(ffs, dec(fcol.getRow()), fcol.getCol());
				addCheckField(ffs, dec(fcol.getRow()), inc(fcol.getCol()));
				addCheckField(ffs, fcol.getRow(), dec(fcol.getCol()));
				addCheckField(ffs, fcol.getRow(), fcol.getCol());
				addCheckField(ffs, fcol.getRow(), inc(fcol.getCol()));
				addCheckField(ffs, inc(fcol.getRow()), dec(fcol.getCol()));
				addCheckField(ffs, inc(fcol.getRow()), fcol.getCol());
				addCheckField(ffs, inc(fcol.getRow()), inc(fcol.getCol()));
			}
		}
		return ffs;
	}

	public void setStone(int row, int col, Stone takeStone) {
		getField(row, col).setStone(takeStone);
	}

	@Override
	public String toString() {
		return new View(this).toString();
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	static class View {
		int minrow = Integer.MAX_VALUE;

		int mincol = Integer.MAX_VALUE;
		int maxrow = 0;
		int maxcol = 0;

		private final ChessMeBoard board;

		public View(ChessMeBoard board) {
			this.board = board;
			Set<BoardField> freeFields = board.getReachableFields();
			for (BoardField ff : freeFields) {
				if (ff.getRow() < minrow)
					minrow = ff.getRow();
				if (ff.getRow() > maxrow)
					maxrow = ff.getRow();
				if (ff.getCol() < mincol)
					mincol = ff.getCol();
				if (ff.getCol() > maxcol)
					maxcol = ff.getCol();
			}
		}

		@Override
		public String toString() {
			StringBuffer buf = new StringBuffer("\n");

			for (int row = 0; row < board.fields.length; row++) {
				if (row < minrow || row > maxrow) {
					continue;
				}
				for (int col = 0; col < board.fields[row].length; col++) {
					if (col < mincol || col > maxcol) {
						continue;
					}
					buf.append(board.fields[row][col].toString() + " ");
				}
				buf.append("\n");
			}
			buf.append(board.comment + " " + minrow + " " + maxrow + " " + mincol + " " + maxcol);
			return buf.toString();
		}
	}
}
