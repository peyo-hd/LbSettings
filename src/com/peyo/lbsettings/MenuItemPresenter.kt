package com.peyo.lbsettings

import android.content.Context
import androidx.leanback.widget.Presenter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class MenuItemPresenter : Presenter() {

    private class MenuItemViewHolder internal constructor(v: View) : Presenter.ViewHolder(v) {
        val mIconView: ImageView
        val mTitleView: TextView

        init {
            mIconView = v.findViewById(R.id.icon) as ImageView
            mTitleView = v.findViewById(R.id.title) as TextView
        }
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
        if (item is MenuItem && viewHolder is MenuItemViewHolder) {
            viewHolder.mIconView.setImageResource(item.getIconRes(
                    viewHolder.mTitleView.context))
            viewHolder.mTitleView.setText(item.getTitleRes(
                    viewHolder.mTitleView.context))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        val inflater = parent.context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.browse_item, parent, false)
        return MenuItemViewHolder(v)
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {
        if (viewHolder is MenuItemViewHolder) {
            viewHolder.mIconView.setImageBitmap(null)
        }
    }

}
