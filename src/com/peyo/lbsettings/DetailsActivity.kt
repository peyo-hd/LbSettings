package com.peyo.lbsettings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter
import androidx.leanback.widget.Action
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ClassPresenterSelector
import androidx.leanback.widget.DetailsOverviewRow
import androidx.leanback.widget.FullWidthDetailsOverviewRowPresenter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener

import com.google.gson.Gson

import java.io.IOException
import java.io.InputStream

class DetailsActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }

    class MenuDetailsFragment : DetailsSupportFragment() {
        private var titleRes: Int = 0
        private var iconRes: Int = 0

        private val overview: DetailsOverviewRow
            get() {
                val adapter = ArrayObjectAdapter()
                adapter.add(Action(1, "OK"))
                adapter.add(Action(2, "Cancel"))

                val overview = DetailsOverviewRow("Menu Item Details")
                overview.actionsAdapter = adapter
                overview.imageDrawable = resources.getDrawable(iconRes, null)

                return overview
            }

        private val row: ListRow
            get() {
                val json = inputStreamToString(resources.openRawResource(R.raw.personal))
                val items = Gson().fromJson(json, Array<MenuItem>::class.java)

                val row = ArrayObjectAdapter(MenuItemPresenter())
                for (item in items) {
                    row.add(item)
                }

                return ListRow(HeaderItem(resources.getString(R.string.personal)), row)
            }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            titleRes = activity!!.intent.getIntExtra("TitleRes", 0)
            iconRes = activity!!.intent.getIntExtra("IconRes", 0)

            val selector = ClassPresenterSelector()
            selector.addClassPresenter(DetailsOverviewRow::class.java,
                    FullWidthDetailsOverviewRowPresenter(
                            DetailsDescriptionPresenter()))
            selector.addClassPresenter(ListRow::class.java, ListRowPresenter())

            val rows = ArrayObjectAdapter(selector)
            rows.add(overview)
            rows.add(row)
            adapter = rows

            onItemViewClickedListener = OnItemViewClickedListener { _, item, _, _ ->
                if (item is Action) {
                    if (item.id == 1L) {
                        val intent = Intent(context, GuidedStepActivity::class.java)
                        intent.putExtra("TitleRes", titleRes)
                        intent.putExtra("IconRes", iconRes)
                        startActivity(intent)
                    }
                } else if (item is MenuItem) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    intent.putExtra("IconRes", item.getIconRes(context!!))
                    intent.putExtra("TitleRes", item.getTitleRes(context!!))
                    startActivity(intent)
                }
                activity!!.finish()
            }
        }

        inner class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {
            override fun onBindDescription(
                    vh: AbstractDetailsDescriptionPresenter.ViewHolder, item: Any) {
                vh.title.text = resources.getString(titleRes)
                vh.subtitle.text = "Menu Item Details"
                vh.body.text = ("Lorem ipsum dolor sit amet, consectetur "
                        + "adipisicing elit, sed do eiusmod tempor incididunt ut labore "
                        + " et dolore magna aliqua. Ut enim ad minim veniam, quis "
                        + "nostrud exercitation ullamco laboris nisi ut aliquip ex ea "
                        + "commodo consequat.")
            }
        }

        private fun inputStreamToString(inputStream: InputStream): String? {
            try {
                val bytes = ByteArray(inputStream.available())
                inputStream.read(bytes, 0, bytes.size)
                return String(bytes)
            } catch (e: IOException) {
                return null
            }

        }
    }

}
