package com.peyo.lbsettings;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class MenuDetailsFragment extends DetailsFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ClassPresenterSelector selector = new ClassPresenterSelector();
        selector.addClassPresenter(DetailsOverviewRow.class,
                new FullWidthDetailsOverviewRowPresenter(new DetailsDescriptionPresenter()));
        selector.addClassPresenter(ListRow.class, new ListRowPresenter());

        ArrayObjectAdapter rows = new ArrayObjectAdapter(selector);
        rows.add(getOverview());
        rows.add(getRow());
        setAdapter(rows);

        setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder vh, Object item,
                                      RowPresenter.ViewHolder rowViewHolder, Row row) {
                getActivity().finish();
            }
        });
    }

    public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {
        @Override
        protected void onBindDescription(
                AbstractDetailsDescriptionPresenter.ViewHolder vh, Object item) {
            int titleRes = getActivity().getIntent().getIntExtra("TitleRes", 0);
            vh.getTitle().setText(getResources().getString(titleRes));
            vh.getSubtitle().setText("Menu Item Details");
            vh.getBody().setText("Lorem ipsum dolor sit amet, consectetur "
                    + "adipisicing elit, sed do eiusmod tempor incididunt ut labore "
                    + " et dolore magna aliqua. Ut enim ad minim veniam, quis "
                    + "nostrud exercitation ullamco laboris nisi ut aliquip ex ea "
                    + "commodo consequat.");
        }
    }

    DetailsOverviewRow getOverview() {
        DetailsOverviewRow overview = new DetailsOverviewRow("Menu Item Details");

        int iconRes = getActivity().getIntent().getIntExtra("IconRes", 0);
        Drawable drawable = getContext().getDrawable(iconRes);
        overview.setImageDrawable(drawable);

        overview.addAction(new Action(1, "Confirm"));
        overview.addAction(new Action(2, "Cancel"));

        return overview;
    }

    ListRow getRow() {
        Resources res = getContext().getResources();

        String json = inputStreamToString(res.openRawResource(R.raw.personal));
        MenuItem[] items =  new Gson().fromJson(json, MenuItem[].class);

        ArrayObjectAdapter row = new ArrayObjectAdapter(new MenuItemPresenter());
        for (MenuItem item : items) {
            row.add(item);
        }

        return new ListRow(new HeaderItem(res.getString(R.string.personal)), row);
    }

    String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            return new String(bytes);
        } catch (IOException e) {
            return null;
        }
    }
}
