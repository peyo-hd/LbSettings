package com.peyo.lbsettings;

import android.app.Activity;
import android.content.Intent;
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

public class DetailsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
	}

	public static class MenuDetailsFragment extends DetailsFragment {
		int titleRes;
		int iconRes;
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			titleRes = getActivity().getIntent().getIntExtra("TitleRes", 0);
			iconRes = getActivity().getIntent().getIntExtra("IconRes", 0);

			ClassPresenterSelector selector = new ClassPresenterSelector();
			selector.addClassPresenter(DetailsOverviewRow.class,
					new FullWidthDetailsOverviewRowPresenter(
							new com.peyo.lbsettings.DetailsActivity.MenuDetailsFragment.DetailsDescriptionPresenter()));
			selector.addClassPresenter(ListRow.class, new ListRowPresenter());

			ArrayObjectAdapter rows = new ArrayObjectAdapter(selector);
			rows.add(getOverview());
			rows.add(getRow());
			setAdapter(rows);

			setOnItemViewClickedListener(new OnItemViewClickedListener() {
				@Override
				public void onItemClicked(Presenter.ViewHolder vh, Object item,
										  RowPresenter.ViewHolder rowViewHolder, Row row) {
					if (item instanceof Action) {
						Action action = (Action) item;
						if (action.getId() == 1) {
							Intent intent = new Intent(getContext(), GuidedStepActivity.class);
							intent.putExtra("TitleRes", titleRes);
							intent.putExtra("IconRes", iconRes);
							startActivity(intent);
						}
					} else if (item instanceof MenuItem) {
						Intent intent = new Intent(getContext(), DetailsActivity.class);
						MenuItem menuItem = (MenuItem) item;
						intent.putExtra("IconRes", menuItem.getIconRes(getContext()));
						intent.putExtra("TitleRes", menuItem.getTitleRes(getContext()));
						startActivity(intent);
					}
					getActivity().finish();
				}
			});
		}

		public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {
			@Override
			protected void onBindDescription(
					AbstractDetailsDescriptionPresenter.ViewHolder vh, Object item) {
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

			Drawable drawable = getResources().getDrawable(iconRes);
			overview.setImageDrawable(drawable);

			overview.addAction(new Action(1, "OK"));
			overview.addAction(new Action(2, "Cancel"));

			return overview;
		}

		ListRow getRow() {
			String json = inputStreamToString(getResources().openRawResource(R.raw.personal));
			MenuItem[] items =  new Gson().fromJson(json, MenuItem[].class);

			ArrayObjectAdapter row = new ArrayObjectAdapter(new MenuItemPresenter());
			for (MenuItem item : items) {
				row.add(item);
			}

			return new ListRow(new HeaderItem(getResources().getString(R.string.personal)), row);
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
