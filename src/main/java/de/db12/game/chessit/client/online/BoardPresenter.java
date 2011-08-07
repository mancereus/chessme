package de.db12.game.chessit.client.online;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

import de.db12.game.chessit.client.event.MoveStoneEvent;
import de.db12.game.chessit.client.event.MoveStoneEventHandler;
import de.db12.game.chessit.shared.game.ChessMeBoard;
import de.db12.game.chessit.shared.game.ChessMeGame;
import de.db12.game.chessit.shared.game.ChessMeGame.Color;
import de.db12.game.chessit.shared.game.ChessMeGame.Stone;
import de.db12.game.chessit.shared.game.ChessMePlayer;
import de.db12.game.chessit.shared.game.Field;
import de.db12.game.chessit.shared.game.Player;

public class BoardPresenter extends Presenter<BoardPresenter.MyView, BoardPresenter.MyProxy> implements
		MoveStoneEventHandler {

	public interface MyView extends View {
		HasWidgets getWhiteHand();

		HasWidgets getBlackHand();

		HasWidgets getWhiteDrop();

		HasWidgets getBlackDrop();

		HasWidgets getHelp();

		AbsolutePanel getBoard();

		AbsolutePanel getTable();
	}

	@ProxyCodeSplit
	@NameToken(nameToken)
	public interface MyProxy extends ProxyPlace<BoardPresenter> {
	}

	public static final String nameToken = "!online";

	ChessMeGame game;

	// PickupDragController dragController = null;

	@Inject
	public BoardPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy);
		List<Player> players = new ArrayList<Player>();
		ChessMeBoard board = new ChessMeBoard();
		players.add(new ChessMePlayer(Color.white, board));
		players.add(new ChessMePlayer(Color.black, board));
		game = new ChessMeGame(board, players);
		// dragController = new PickupDragController(getView().getBoard(), false);
		refreshView();
		eventBus.addHandler(MoveStoneEvent.getType(), this);
	}

	@Override
	protected void revealInParent() {
		RevealRootContentEvent.fire(this, this);
	}

	private void refreshView() {
		clearBoard();
		game.init();
		showBoard();

	}

	private void showBoard() {
		refreshHands();
		refreshBoard();
		refreshDrop();

	}

	private void refreshDrop() {
	}

	private void refreshBoard() {
		int pxsize = 120;
		de.db12.game.chessit.shared.game.ChessMeBoard.View bv = new ChessMeBoard.View(game.getBoard());
		pxsize = 700 / bv.getMaxSize();
		showHelp();
		for (Field field : bv.getFields()) {
			FieldView stone = new FieldView(field, pxsize);
			int rowpos = field.getRow();// - game.getBoard().getXOffset();
			int colpos = field.getCol();// - game.getBoard().getYOffset();
			getView().getBoard().add(stone, 20 + pxsize * rowpos, pxsize * colpos);
			// dragController.registerDropController(new FieldDropController(getEventBus(), stone));
			// if (field.getStone().getPlayer() == Player.white && game.getState() == State.whitedraw)
			// dragController.makeDraggable(stone);
			// if (field.getStone().getPlayer() == Player.black && game.getState() == State.blackdraw)
			// dragController.makeDraggable(stone);
		}
	}

	private void refreshHands() {

		getView().getWhiteHand().clear();
		getView().getBlackHand().clear();
		for (Stone handstone : game.getPlayers().get(0).getHand()) {
			StoneView stoneview = new StoneView(handstone, 50);
			getView().getWhiteHand().add(stoneview);
		}
		for (Stone handstone : game.getPlayers().get(1).getHand()) {
			StoneView stoneview = new StoneView(handstone, 50);
			getView().getBlackHand().add(stoneview);
		}
	}

	private void showHelp() {
		// getView().getHelp().clear();
		// String text;
		// switch (game.getState()) {
		// case whitedraw:
		// text = "white must draw";
		// break;
		// case whiteset:
		// text = "white can set";
		// break;
		// case blackdraw:
		// text = "black must draw";
		// break;
		// case blackset:
		// text = "black can set";
		// break;
		//
		// default:
		// text = "";
		// break;
		// }
		// getView().getHelp().add(new Label(text));
		// if (game.getState() == State.blackset || game.getState() == State.whiteset) {
		// Button jump = new Button("ignore set");
		// jump.addClickHandler(new ClickHandler() {
		//
		// @Override
		// public void onClick(ClickEvent event) {
		// refreshView();
		// }
		// });
		// getView().getHelp().add(jump);
		// }
	}

	private void clearBoard() {
		// dragController.unregisterDropControllers();
		getView().getBoard().clear();
	}

	public void moveStone(Field source, Stone stone, Field target) {
		game.getBoard().moveFromTo(source, target);
	}

	@Override
	public void onStoneMoved(MoveStoneEvent event) {
		moveStone(event.getOrigin(), event.getStone(), event.getTarget());
		refreshView();
	}
}
