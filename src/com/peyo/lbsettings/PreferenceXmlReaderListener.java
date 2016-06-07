package com.peyo.lbsettings;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.peyo.lbsettings.XmlReader.XmlReaderListener;

public class PreferenceXmlReaderListener implements XmlReaderListener  {
	private final ArrayObjectAdapter mRow;
	private BrowseInfo mInfo;
	private Context mContext;
	
	PreferenceXmlReaderListener(BrowseInfo info, int headerId, ArrayObjectAdapter row) {
		mContext = info.mContext;
		mInfo = info;
	    mRow = row;
	}

	@Override
	public void handleRequestedNode(Context context, XmlResourceParser parser,
	        AttributeSet attrs) throws XmlPullParserException, IOException {
	    TypedArray sa = context.getResources().obtainAttributes(attrs,
	            R.styleable.Preference);
	
	    String key = getStringFromTypedArray(sa,
	            R.styleable.Preference_key);
	    String title = getStringFromTypedArray(sa,
	            R.styleable.Preference_title);
	    int iconRes = sa.getResourceId(R.styleable.Preference_icon,
	            R.drawable.settings_default_icon);
	    sa.recycle();
        mRow.add(new MenuItem.Builder()
        .id(mInfo.mNextItemId++)
        .title(title)
        .imageResourceId(iconRes)
        .build());
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
