package de.db12.game.chessit.shared.game;

import java.util.List;

public interface Board{

	List<BoardField> getFreeFields();

	int getNextMinValue();

	void init();

}
