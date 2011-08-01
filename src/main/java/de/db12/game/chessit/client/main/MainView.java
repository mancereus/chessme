package de.db12.game.chessit.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class MainView extends ViewImpl implements MainPresenter.MyView {

    private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);

    interface MainViewUiBinder extends UiBinder<HTMLPanel, MainView> {
    }

    @UiField
    HTMLPanel panel;

    @Inject
    public MainView(String firstName) {
        panel = uiBinder.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }
}
