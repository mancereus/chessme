package de.db12.game.chessit.client.online;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;

public interface BoardResources extends ClientBundle {
    public static final BoardResources INSTANCE = GWT.create(BoardResources.class);

    @Source("board.css")
    public BoardCss style();

    interface BoardCss extends CssResource {
        String enterBG();

        String border();
    }

    // @Source("config.xml")
    // public TextResource initialConfiguration();
    //
    // @Source("manual.pdf")
    // public DataResource ownersManual();

    @Source("images/bauer_black.png")
    DataResource bpawn();

    @Source("images/bauer_white.png")
    DataResource wpawn();

    @Source("images/dame_black.png")
    DataResource bqueen();

    @Source("images/dame_white.png")
    DataResource wqueen();

    @Source("images/koenig_black.png")
    DataResource bking();

    @Source("images/koenig_white.png")
    DataResource wking();

    @Source("images/laeufer_black.png")
    DataResource bbishop();

    @Source("images/laeufer_white.png")
    DataResource wbishop();

    @Source("images/springer_black.png")
    DataResource bknight();

    @Source("images/springer_white.png")
    DataResource wknight();

    @Source("images/turm_black.png")
    DataResource brook();

    @Source("images/turm_white.png")
    DataResource wrook();

    @Source("images/empty.png")
    DataResource empty();

}