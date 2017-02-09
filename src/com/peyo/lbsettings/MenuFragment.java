package com.peyo.lbsettings;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class MenuFragment extends BrowseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayObjectAdapter rows = new ArrayObjectAdapter(new ListRowPresenter());
        rows.add(getRow(R.string.device, R.raw.device));
        rows.add(getRow(R.string.preferences, R.raw.preferences));
        rows.add(getRow(R.string.personal, R.raw.personal));
        setAdapter(rows);

        setHeadersState(HEADERS_DISABLED);
    }

    ListRow getRow(int titleResId, int jsonResId) {
        Resources res = getContext().getResources();

        String json = inputStreamToString(res.openRawResource(jsonResId));
        MenuItem[] items =  new Gson().fromJson(json, MenuItem[].class);

        ArrayObjectAdapter row = new ArrayObjectAdapter(new MenuItemPresenter());
        for (MenuItem item : items) {
            row.add(item);
        }

        return new ListRow(new HeaderItem(res.getString(titleResId)), row);
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
