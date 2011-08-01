package de.db12.game.chessit.shared.game;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import de.db12.game.chessit.shared.game.ChessMeGame.Card;

public class BoardField  {
	private static final Logger log = LoggerFactory.getLogger("cf");

	private Card card;
	private List<BoardField> parents = Lists.newArrayList();
	private Board board;

	public Card getCard() {
		return card;
	}

	public BoardField(Board board) {
		this.board = board;
	}

	public boolean isFreeToUse() {
		if (getCard() != null)
			return false;
		for (BoardField par : parents) {
			if (par.getCard() == null)
				return false;
		}
		return true;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public void addParent(BoardField cardField) {
		if (cardField == null)
			return;
		parents.add(cardField);

	}

	@Override
	public String toString() {
		if (getCard() == null)
			return "...";
		return getCard().toString();
	}

	public List<BoardField> getParents() {
		return parents;

	}


}
