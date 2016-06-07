package com.peyo.lbsettings;

import android.content.Context;
import android.content.res.Resources;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuItemPresenter extends Presenter {
	
    private static class MenuItemViewHolder extends ViewHolder {
        public final ImageView mIconView;
        public final TextView mTitleView;
        public final TextView mDescriptionView;

        MenuItemViewHolder(View v) {
            super(v);
            mIconView = (ImageView) v.findViewById(R.id.icon);
            mTitleView = (TextView) v.findViewById(R.id.title);
            mDescriptionView = (TextView) v.findViewById(R.id.description);
            
        }

    }

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, Object item) {
	      if (item instanceof MenuItem && viewHolder instanceof MenuItemViewHolder) {
	            final MenuItem menuItem = (MenuItem) item;
	            MenuItemViewHolder menuItemViewHolder = (MenuItemViewHolder) viewHolder;

	            prepareTextView(menuItemViewHolder.mTitleView, menuItem.getTitle());
	            boolean hasDescription = prepareTextView(menuItemViewHolder.mDescriptionView,
	                    menuItem.getDescriptionGetter() == null ? null
	                    : menuItem.getDescriptionGetter().getText());

	            Resources res = menuItemViewHolder.mTitleView.getContext().getResources();
	            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams)
	                    menuItemViewHolder.mTitleView.getLayoutParams();
	            if (hasDescription) {
	                lp.bottomMargin = (int) res.getDimension(R.dimen.browse_item_title_marginBottom);
	                menuItemViewHolder.mTitleView.setSingleLine(true);
	                menuItemViewHolder.mTitleView.setLines(1);
	            } else {
	                lp.bottomMargin = (int) res.getDimension(R.dimen.browse_item_description_marginBottom);
	                menuItemViewHolder.mTitleView.setSingleLine(false);
	                menuItemViewHolder.mTitleView.setLines(2);
	            }
	            menuItemViewHolder.mTitleView.setLayoutParams(lp);
                menuItemViewHolder.mIconView.setImageResource(menuItem.getIconRes());
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
	
    private boolean prepareTextView(TextView textView, String title) {
        boolean hasTextView = !TextUtils.isEmpty(title);
        if (hasTextView) {
            textView.setText(title);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
        return hasTextView;
    }
}
