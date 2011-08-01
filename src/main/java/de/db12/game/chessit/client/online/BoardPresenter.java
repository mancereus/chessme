package de.db12.game.chessit.client.online;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.EventBus;
import com.gwtplatform.mvp.client.PresenterImpl;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

import de.db12.game.chessit.client.event.MoveStoneEvent;
import de.db12.game.chessit.client.event.MoveStoneEventHandler;
import de.db12.game.chessit.client.online.model.BoardModel;
import de.db12.game.chessit.client.online.model.Field;
import de.db12.game.chessit.client.online.model.InPlace;
import de.db12.game.chessit.client.online.model.Place;
import de.db12.game.chessit.client.online.model.Stone;

public class BoardPresenter extends PresenterImpl<BoardPresenter.MyView, BoardPresenter.MyProxy> implements
        MoveStoneEventHandler {
    public enum Player {
        white, black, none
    }

    public enum State {
        whitedraw(Player.white), whiteset(Player.white), blackdraw(Player.black), blackset(Player.black);
        private Player player;

        State(Player player) {
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }

        public State next() {
            return State.values()[(this.ordinal() + 1) % State.values().length];

        }
    }

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

    BoardModel model;
    PickupDragController dragController = null;

    @Inject
    public BoardPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy);
        model = new BoardModel(30);
        dragController = new PickupDragController(view.getBoard(), false);
        refreshView();
        eventBus.addHandler(MoveStoneEvent.getType(), this);
    }

    @Override
    protected void revealInParent() {
        RevealRootContentEvent.fire(eventBus, this);
    }

    private void refreshView() {
        clearBoard();
        model.nextState();
        showBoard();

    }

    private void showBoard() {
        // int height = boardview.getOffsetHeight();
        // int width = boardview.getOffsetWidth();
        // int pxsize = Math.max(Math.min(height, width) / 8, 30);
        refreshHands();
        refreshBoard();
        refreshDrop();

    }

    private void refreshDrop() {
        view.getWhiteDrop().clear();
        for (int i = 0; i < model.getwDrop().size(); i++) {
            StoneView stoneview = new StoneView(new InPlace(i, model.getwDrop().get(i)), 50);
            view.getWhiteDrop().add(stoneview);
        }
        view.getBlackDrop().clear();
        for (int i = 0; i < model.getbDrop().size(); i++) {
            StoneView stoneview = new StoneView(new InPlace(i, model.getbDrop().get(i)), 50);
            view.getBlackDrop().add(stoneview);
        }
    }

    private void refreshBoard() {
        int pxsize = 120;
        BoardSize bsize = new BoardSize();
        for (Field field : model.getBoard().getFields()) {
            int xpos = field.getX();
            int ypos = field.getY();
            bsize.updateX(xpos);
            bsize.updateY(ypos);
        }
        pxsize = 700 / bsize.getMaxSize();
        showHelp();
        for (Field field : model.getBoard().getFields()) {
            StoneView stone = new StoneView(field, pxsize);
            int xpos = field.getX() - model.getBoard().getXOffset();
            int ypos = field.getY() - model.getBoard().getYOffset();
            view.getBoard().add(stone, 20 + pxsize * xpos, pxsize * ypos);
            dragController.registerDropController(new FieldDropController(eventBus, stone));
            if (field.getStone().getPlayer() == Player.white && model.getState() == State.whitedraw)
                dragController.makeDraggable(stone);
            if (field.getStone().getPlayer() == Player.black && model.getState() == State.blackdraw)
                dragController.makeDraggable(stone);
        }
    }

    private void refreshHands() {
        view.getWhiteHand().clear();
        view.getWhiteHand().add(new Label("White"));
        for (int i = 0; i < Math.min(model.getWHand().size(), 3); i++) {
            StoneView stoneview = new StoneView(new InPlace(i, model.getWHand().get(i)), 50);
            view.getWhiteHand().add(stoneview);
            if (model.getState() == State.whiteset)
                dragController.makeDraggable(stoneview);
        }
        view.getBlackHand().clear();
        view.getWhiteHand().add(new Label("Black"));
        for (int i = 0; i < Math.min(model.getBHand().size(), 3); i++) {
            StoneView stoneview = new StoneView(new InPlace(i, model.getBHand().get(i)), 50);
            view.getBlackHand().add(stoneview);
            if (model.getState() == State.blackset)
                dragController.makeDraggable(stoneview);
        }
    }

    private void showHelp() {
        view.getHelp().clear();
        String text;
        switch (model.getState()) {
        case whitedraw:
            text = "white must draw";
            break;
        case whiteset:
            text = "white can set";
            break;
        case blackdraw:
            text = "black must draw";
            break;
        case blackset:
            text = "black can set";
            break;

        default:
            text = "";
            break;
        }
        view.getHelp().add(new Label(text));
        if (model.getState() == State.blackset || model.getState() == State.whiteset) {
            Button jump = new Button("ignore set");
            jump.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    refreshView();
                }
            });
            view.getHelp().add(jump);
        }
    }

    private void clearBoard() {
        dragController.unregisterDropControllers();
        view.getBoard().clear();
    }

    public void moveStone(Place origin, Stone stone, Place place) {
        model.moveStone(origin, stone, place);
    }

    @Override
    public void onStoneMoved(MoveStoneEvent event) {
        moveStone(event.getOrigin(), event.getStone(), event.getTarget());
        refreshView();
    }
}
