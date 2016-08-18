package com.peyo.lbsettings;

import android.content.Context;
import android.content.res.Resources;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.util.SparseArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.google.gson.Gson;

public class BrowseInfo {
    protected final SparseArray<ArrayObjectAdapter> mRows = new SparseArray<ArrayObjectAdapter>();
    protected final ArrayList<HeaderItem> mHeaderItems = new ArrayList<HeaderItem>();
    private final ClassPresenterSelector mPresenterSelector = new ClassPresenterSelector();
	public Context mContext;

    BrowseInfo(Context context) {
        mContext = context;
        mHeaderItems.clear();
        mRows.clear();
        mPresenterSelector.addClassPresenter(MenuItem.class, new MenuItemPresenter());
    }

    public ArrayObjectAdapter getRows() {
        ArrayObjectAdapter rows = new ArrayObjectAdapter();

        for (int i = 0, size = mHeaderItems.size(); i < size; i++) {
            HeaderItem headerItem = mHeaderItems.get(i);
            ArrayObjectAdapter adapter = mRows.get((int) headerItem.getId());
            adapter.setPresenterSelector(mPresenterSelector);
            rows.add(new ListRow(headerItem, adapter));
        }
        return rows;
    }
    
    void init() {
        mHeaderItems.clear();
        mRows.clear();

        Resources res = mContext.getResources();
        mHeaderItems.add(new HeaderItem(1, "Device"));
        mRows.put(1, getRow (inputStreamToString(res.openRawResource(R.raw.device))));
        mHeaderItems.add(new HeaderItem(2, "Personal"));
        mRows.put(2, getRow (inputStreamToString(res.openRawResource(R.raw.personal))));
        mHeaderItems.add(new HeaderItem(3, "Preferences"));
        mRows.put(3, getRow (inputStreamToString(res.openRawResource(R.raw.preferences))));

    }

    ArrayObjectAdapter getRow(String json) {
        MenuItem[] items =  new Gson().fromJson(json, MenuItem[].class);
        ArrayObjectAdapter row = new ArrayObjectAdapter(new MenuItemPresenter());
        for (MenuItem item : items) {
            row.add(item);
        }
        return row;
    }

    String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }
}
