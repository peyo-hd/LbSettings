package com.peyo.lbsettings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import androidx.leanback.widget.OnItemViewClickedListener

import com.google.gson.Gson

import java.io.IOException
import java.io.InputStream

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    class MenuFragment : BrowseSupportFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val rows = ArrayObjectAdapter(ListRowPresenter())
            rows.add(getRow(R.string.device, R.raw.device))
            rows.add(getRow(R.string.preferences, R.raw.preferences))
            rows.add(getRow(R.string.personal, R.raw.personal))
            adapter = rows

            headersState = BrowseSupportFragment.HEADERS_DISABLED

            onItemViewClickedListener = OnItemViewClickedListener { _, item, _, _ ->
                val intent = Intent(context, DetailsActivity::class.java)
                val menuItem = item as MenuItem
                intent.putExtra("IconRes", menuItem.getIconRes(context!!))
                intent.putExtra("TitleRes", menuItem.getTitleRes(context!!))
                startActivity(intent)
            }
        }

        private fun getRow(titleResId: Int, jsonResId: Int): ListRow {
            val res = context!!.resources

            val json = inputStreamToString(res.openRawResource(jsonResId))
            val items = Gson().fromJson(json, Array<MenuItem>::class.java)

            val row = ArrayObjectAdapter(MenuItemPresenter())
            for (item in items) {
                row.add(item)
            }

            return ListRow(HeaderItem(res.getString(titleResId)), row)
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
