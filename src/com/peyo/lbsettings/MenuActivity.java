package com.peyo.lbsettings;

import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;

public class MenuActivity extends Activity {
	private static final String TAG_BROWSE_FRAGMENT = "browse_fragment";
	private BrowseFragment mBrowseFragment;
    private BrowseInfo mBrowseInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        mBrowseInfo = new BrowseInfo(this);
        mBrowseInfo.init();
		
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBrowseFragment = (BrowseFragment) getFragmentManager().findFragmentByTag(
                TAG_BROWSE_FRAGMENT);
        if (mBrowseFragment == null) {
            mBrowseFragment = new BrowseFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment, mBrowseFragment, TAG_BROWSE_FRAGMENT)
                    .commit();
        }
        
        ClassPresenterSelector rowPresenterSelector = new ClassPresenterSelector();
        rowPresenterSelector.addClassPresenter(ListRow.class, new ListRowPresenter());

        ObjectAdapter adapter = mBrowseInfo.getRows();
        adapter.setPresenterSelector(rowPresenterSelector);
        
        mBrowseFragment.setAdapter(adapter);
        mBrowseFragment.setHeadersState(BrowseFragment.HEADERS_DISABLED);
	}
}
