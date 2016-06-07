package com.peyo.lbsettings;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.preference.PreferenceActivity;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

public class BrowseInfo {
    protected final SparseArray<ArrayObjectAdapter> mRows = new SparseArray<ArrayObjectAdapter>();
    protected final ArrayList<HeaderItem> mHeaderItems = new ArrayList<HeaderItem>();
    private final ClassPresenterSelector mPresenterSelector = new ClassPresenterSelector();
	public Context mContext;
    public int mNextItemId;

    BrowseInfo(Context context) {
        mContext = context;
        mHeaderItems.clear();
        mRows.clear();
        mPresenterSelector.addClassPresenter(MenuItem.class, new MenuItemPresenter());
        mNextItemId = 0;
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
        new XmlReader(mContext, R.xml.main, "preference-headers", "header",
                new HeaderXmlReaderListener()).read();
    }
    
    private class HeaderXmlReaderListener implements XmlReader.XmlReaderListener {
        @Override
        public void handleRequestedNode(Context context, XmlResourceParser parser,
                AttributeSet attrs)
                throws XmlPullParserException, IOException {
            TypedArray sa = mContext.getResources().obtainAttributes(attrs,
                    R.styleable.PreferenceHeader);
            final int headerId = sa.getResourceId(
                    R.styleable.PreferenceHeader_id,
                    (int) PreferenceActivity.HEADER_ID_UNDEFINED);
            String title = getStringFromTypedArray(sa,
                    R.styleable.PreferenceHeader_header_title);
            int preferenceRes = sa.getResourceId(R.styleable.PreferenceHeader_preference, 0);
            sa.recycle();
            mHeaderItems.add(new HeaderItem(headerId, title));
            final ArrayObjectAdapter currentRow = new ArrayObjectAdapter();
            mRows.put(headerId, currentRow);
            new XmlReader(mContext, preferenceRes, "PreferenceScreen", "Preference",
                        new PreferenceXmlReaderListener(BrowseInfo.this, headerId, currentRow)).read();
        }
    }
    
    private String getStringFromTypedArray(TypedArray sa, int resourceId) {
        String value = null;
        TypedValue tv = sa.peekValue(resourceId);
        if (tv != null && tv.type == TypedValue.TYPE_STRING) {
            if (tv.resourceId != 0) {
                value = mContext.getString(tv.resourceId);
            } else {
                value = tv.string.toString();
            }
        }
        return value;
    }
}
