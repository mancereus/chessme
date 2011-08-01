package de.db12.game.chessit.client.online;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.user.client.ui.Image;

import de.db12.game.chessit.client.online.model.Place;
import de.db12.game.chessit.client.online.model.Stone;

public class StoneView extends Image {

    private static BoardResources res = GWT.create(BoardResources.class);
    private final Place field;

    public StoneView(Place place, int pxsize) {
        super(getDataResource(place.getStone()).getUrl());
        setWidth(pxsize + "px");
        setHeight(pxsize + "px");
        addStyleName(res.style().border());
        this.field = place;
    }

    public static DataResource getDataResource(Stone stone) {
        if (stone == null)
            return res.empty();
        switch (stone.getType()) {
        case bbishop:
            return res.bbishop();
        case bking:
            return res.bking();
        case bqueen:
            return res.bqueen();
        case bpawn:
            return res.bpawn();
        case brook:
            return res.brook();
        case bknight:
            return res.bknight();
        case wbishop:
            return res.wbishop();
        case wking:
            return res.wking();
        case wqueen:
            return res.wqueen();
        case wpawn:
            return res.wpawn();
        case wrook:
            return res.wrook();
        case wknight:
            return res.wknight();
        case empty:
            return res.empty();
        default:
            break;
        }
        return null;
    }

    public Place getPlace() {
        return field;
    }
}
