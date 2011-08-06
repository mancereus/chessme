package de.db12.game.chessit.shared.game;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public abstract class BaseGame implements Game {
	private static final Logger log = LoggerFactory.getLogger(BaseGame.class);

	protected Board board;
	protected List<Player> players;

	protected int round = 0;

	protected boolean finished;

	@Inject
	public BaseGame(Board board, List<Player> players) {
		this.board = board;
		this.players = players;
	}

	@Override
	public abstract void finish();

	@Override
	public abstract void init();

	@Override
	public void initRound() {
		round++;
		board.initRound();
		for (Player player : players) {
			player.initRound();
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void setFinished() {
		finished = true;
	}

	private void playRound() {
		initRound();
		for (Player player : players) {
			player.move();
			if (isFinished())
				break;
			log.info(toString());
		}
	}

	@Override
	public void start() {
		while (!isFinished()) {
			playRound();
		}
		finish();
	}
}
