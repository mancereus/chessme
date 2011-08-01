package de.db12.game.chessit.shared.game;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class ChessMeGame extends BaseGame {
	public enum Type {
		king, queen, pawn, rook, bishop, horse
	}
	public enum Color {
		white, black
	}

	public enum Card {
		whiteking(Color.white, Type.king), whitequeen(Color.white, Type.queen), whitepawn1(
				Color.white, Type.pawn), whitepawn2(Color.white, Type.pawn), whitepawn3(
				Color.white, Type.pawn), whitepawn4(Color.white, Type.pawn), whitepawn5(
				Color.white, Type.pawn), whitepawn6(Color.white, Type.pawn), whitepawn7(
				Color.white, Type.pawn), whitepawn8(Color.white, Type.pawn), whiterook1(
				Color.white, Type.rook), whiterook2(Color.white, Type.rook), whitebishop1(
				Color.white, Type.bishop), whitebishop2(Color.white,
				Type.bishop), whitehorse1(Color.white, Type.horse), whitehorse2(
				Color.white, Type.horse),
		blackking(Color.black, Type.king), blackqueen(Color.black, Type.queen), blackpawn1(
				Color.black, Type.pawn), blackpawn2(Color.black, Type.pawn), blackpawn3(
				Color.black, Type.pawn), blackpawn4(Color.black, Type.pawn), blackpawn5(
				Color.black, Type.pawn), blackpawn6(Color.black, Type.pawn), blackpawn7(
				Color.black, Type.pawn), blackpawn8(Color.black, Type.pawn), blackrook1(
				Color.black, Type.rook), blackrook2(Color.black, Type.rook), blackbishop1(
				Color.black, Type.bishop), blackbishop2(Color.black,
				Type.bishop), blackhorse1(Color.black, Type.horse), blackhorse2(
				Color.black, Type.horse);
		Color color;
		Type type;

		Card(Color color, Type type) {
			this.color = color;
			this.type = type;
		}
		public static Iterable<Card> getAllCards(final Color color) {
			Iterable<Card> filter = Iterables.filter((Iterable<Card>) Arrays.asList(values()).iterator(), new Predicate<Card>(){
				@Override
				public boolean apply(Card arg0) {
					return arg0.color == color;
				}});
			return filter;
		}
	}


	public static void main(String[] args) {
		List<Player> players = Lists.newArrayList();
		players.add(new ChessMePlayer(Color.white));
		players.add(new ChessMePlayer(Color.black));
		ChessMeGame game = new ChessMeGame(new ChessMeBoard(), players);
		game.init();

	}

	public ChessMeGame(Board board, List<Player> players) {
		super(board, players);
	}

	@Override
	public List<Player> getSpieler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub

	}

}