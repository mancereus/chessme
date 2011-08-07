package de.db12.game.chessit.client.online;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.user.client.ui.Image;

import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;

public class StoneView extends Image {

	private static BoardResources res = GWT.create(BoardResources.class);
	private final Stone stone;

	public StoneView(Stone handstone, int pxsize) {
		super(getDataResource(handstone).getUrl());
		setWidth(pxsize + "px");
		setHeight(pxsize + "px");
		addStyleName(res.style().border());
		this.stone = handstone;
	}

	public static DataResource getDataResource(Stone stone) {
		if (stone == null)
			return res.empty();
		Color color = stone.color;
		switch (stone.getType()) {
		case bishop:
			return color == Color.white ? res.wbishop() : res.bbishop();
		case king:
			return color == Color.white ? res.wking() : res.bking();
		case queen:
			return color == Color.white ? res.wqueen() : res.bqueen();
		case pawn:
			return color == Color.white ? res.wpawn() : res.bpawn();
		case rook:
			return color == Color.white ? res.wrook() : res.brook();
		case horse:
			return color == Color.white ? res.wknight() : res.bknight();
		default:
			break;
		}
		return null;
	}

	public Stone getStone() {
		return stone;
	}
}
