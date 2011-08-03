package de.db12.game.chessit.shared.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class ChessMeGame extends BaseGame {
	public enum Color {
		white, black;

		public String desc() {
			if (this == white)
				return "w";
			return "b";
		}

		public int dir() {
			if (this == white)
				return -1;
			return 1;
		}
	}

	public enum Stone {
		whiteking(Color.white, Type.king), whitequeen(Color.white, Type.queen), whitepawn1(
				Color.white, Type.pawn), whitepawn2(Color.white, Type.pawn), whitepawn3(
				Color.white, Type.pawn), whitepawn4(Color.white, Type.pawn), whitepawn5(
				Color.white, Type.pawn), whitepawn6(Color.white, Type.pawn), whitepawn7(
				Color.white, Type.pawn), whitepawn8(Color.white, Type.pawn), whiterook1(
				Color.white, Type.rook), whiterook2(Color.white, Type.rook), whitebishop1(
				Color.white, Type.bishop), whitebishop2(Color.white,
				Type.bishop), whitehorse1(Color.white, Type.horse), whitehorse2(
				Color.white, Type.horse), blackking(Color.black, Type.king), blackqueen(
				Color.black, Type.queen), blackpawn1(Color.black, Type.pawn), blackpawn2(
				Color.black, Type.pawn), blackpawn3(Color.black, Type.pawn), blackpawn4(
				Color.black, Type.pawn), blackpawn5(Color.black, Type.pawn), blackpawn6(
				Color.black, Type.pawn), blackpawn7(Color.black, Type.pawn), blackpawn8(
				Color.black, Type.pawn), blackrook1(Color.black, Type.rook), blackrook2(
				Color.black, Type.rook), blackbishop1(Color.black, Type.bishop), blackbishop2(
				Color.black, Type.bishop), blackhorse1(Color.black, Type.horse), blackhorse2(
				Color.black, Type.horse);
		public static List<Stone> getAllStones(final Color color) {
			Iterable<Stone> filter = Iterables.filter(Arrays.asList(values()),
					new Predicate<Stone>() {
						@Override
						public boolean apply(Stone arg0) {
							return arg0.color == color;
						}
					});

			List<Stone> ret = new ArrayList<Stone>();
			Iterables.addAll(ret, filter);
			return ret;
		}

		Color color;

		Type type;

		Stone(Color color, Type type) {
			this.color = color;
			this.type = type;
		}

		@Override
		public String toString() {
			return color.desc() + type.desc();
		}
	}

	public enum Type {
		king("k") {
			@Override
			public List<BoardField> getFields(ChessMeBoard board,
					BoardField source, int dir) {
				List<BoardField> targets = Lists.newArrayList();
				targets.add(board.getField(source.getX() - 1, source.getY()));
				targets.add(board.getField(source.getX() + 1, source.getY()));
				targets.add(board.getField(source.getX() - 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				return targets;
			}

		},
		queen("q") {
			@Override
			public List<BoardField> getFields(ChessMeBoard board,
					BoardField source, int dir) {
				List<BoardField> targets = Lists.newArrayList();
				targets.add(board.getField(source.getX() - 1, source.getY()));
				targets.add(board.getField(source.getX() + 1, source.getY()));
				targets.add(board.getField(source.getX() - 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				return targets;
			}

		},
		pawn("p") {
			@Override
			public List<BoardField> getFields(ChessMeBoard board,
					BoardField source, int dir) {
				List<BoardField> targets = Lists.newArrayList();
				targets.add(board.getField(source.getX() - 1, source.getY()));
				targets.add(board.getField(source.getX() + 1, source.getY()));
				targets.add(board.getField(source.getX() - 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				return targets;
			}

		},
		rook("r") {
			@Override
			public List<BoardField> getFields(ChessMeBoard board,
					BoardField source, int dir) {
				List<BoardField> targets = Lists.newArrayList();
				targets.add(board.getField(source.getX() - 1, source.getY()));
				targets.add(board.getField(source.getX() + 1, source.getY()));
				targets.add(board.getField(source.getX() - 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				return targets;
			}

		},
		bishop("b") {
			@Override
			public List<BoardField> getFields(ChessMeBoard board,
					BoardField source, int dir) {
				List<BoardField> targets = Lists.newArrayList();
				targets.add(board.getField(source.getX() - 1, source.getY()));
				targets.add(board.getField(source.getX() + 1, source.getY()));
				targets.add(board.getField(source.getX() - 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				return targets;
			}

		},
		horse("h") {
			@Override
			public List<BoardField> getFields(ChessMeBoard board,
					BoardField source, int dir) {
				List<BoardField> targets = Lists.newArrayList();
				targets.add(board.getField(source.getX() - 1, source.getY()));
				targets.add(board.getField(source.getX() + 1, source.getY()));
				targets.add(board.getField(source.getX() - 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				targets.add(board.getField(source.getX() + 1, source.getY() + 1
						* dir));
				return targets;
			}

		};

		private String desc;

		Type(String desc) {
			this.desc = desc;
		}

		public String desc() {
			return desc;
		}

		public List<BoardField> getFields(ChessMeBoard board,
				BoardField boardField, int dir) {
			List<BoardField> targets = Lists.newArrayList();

			return targets;
		}
	}

	private static final Logger log = LoggerFactory.getLogger(BaseGame.class);

	public static void main(String[] args) {
		List<Player> players = Lists.newArrayList();
		ChessMeBoard board = new ChessMeBoard();
		players.add(new ChessMePlayer(Color.white, board));
		players.add(new ChessMePlayer(Color.black, board));
		ChessMeGame game = new ChessMeGame(board, players);
		game.init();
		game.start();
	}

	public ChessMeGame(Board board, List<Player> players) {
		super(board, players);
	}

	@Override
	public void finish() {
		log.info(toString());

	}

	@Override
	public void init() {
		board.init();
		for (Player player : players) {
			player.init();
		}
	}

	@Override
	public boolean isFinished() {
		return round > 100;
	}

	@Override
	public String toString() {
		return board.toString() + "\nround: " + round + "\n"
				+ players.get(0).showHand() + "    "
				+ players.get(1).showHand();
	}
}
