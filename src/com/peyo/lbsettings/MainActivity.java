package com.peyo.lbsettings;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
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

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
	}

	public static class MenuFragment extends BrowseFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			ArrayObjectAdapter rows = new ArrayObjectAdapter(new ListRowPresenter());
			rows.add(getRow(R.string.device, R.raw.device));
			rows.add(getRow(R.string.preferences, R.raw.preferences));
			rows.add(getRow(R.string.personal, R.raw.personal));
			setAdapter(rows);

			setHeadersState(HEADERS_DISABLED);

			setOnItemViewClickedListener(new OnItemViewClickedListener() {
				@Override
				public void onItemClicked(Presenter.ViewHolder vh, Object item,
										  RowPresenter.ViewHolder rowViewHolder, Row row) {
					Intent intent = new Intent(getContext(), DetailsActivity.class);
					MenuItem menuItem = (MenuItem) item;
					intent.putExtra("IconRes", menuItem.getIconRes(getContext()));
					intent.putExtra("TitleRes", menuItem.getTitleRes(getContext()));
					startActivity(intent);
				}
			});
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
}
