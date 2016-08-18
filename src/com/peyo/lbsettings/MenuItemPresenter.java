package com.peyo.lbsettings;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuItemPresenter extends Presenter {
	
    private static class MenuItemViewHolder extends ViewHolder {
        public final ImageView mIconView;
        public final TextView mTitleView;

        MenuItemViewHolder(View v) {
            super(v);
            mIconView = (ImageView) v.findViewById(R.id.icon);
            mTitleView = (TextView) v.findViewById(R.id.title);
        }
    }

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, Object item) {
	      if (item instanceof MenuItem && viewHolder instanceof MenuItemViewHolder) {
	            final MenuItem menuItem = (MenuItem) item;
	            MenuItemViewHolder menuItemViewHolder = (MenuItemViewHolder) viewHolder;
                menuItemViewHolder.mIconView.setImageResource(menuItem.getIconRes(
						menuItemViewHolder.mTitleView.getContext()));
                menuItemViewHolder.mTitleView.setText(menuItem.getTitleRes(
					  menuItemViewHolder.mTitleView.getContext()));
	        }
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.browse_item, parent, false);
        return new MenuItemViewHolder(v);
	}

	@Override
	public void onUnbindViewHolder(ViewHolder viewHolder) {
        if (viewHolder instanceof MenuItemViewHolder) {
            MenuItemViewHolder menuItemViewHolder = (MenuItemViewHolder) viewHolder;
            menuItemViewHolder.mIconView.setImageBitmap(null);
        }
	}
	
}
