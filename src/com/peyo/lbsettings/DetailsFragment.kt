package com.peyo.lbsettings

import android.content.Intent
import android.os.Bundle
import androidx.leanback.app.DetailsSupportFragment
import androidx.leanback.widget.*
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream

class DetailsFragment : DetailsSupportFragment() {
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
        val args = DetailsFragmentArgs.fromBundle(arguments!!)
        titleRes = args.titleResource
        iconRes = args.iconResource

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
                    findNavController().navigate(DetailsFragmentDirections.actionDetailsToGuided(
                                    titleRes, iconRes))
                } else {
                    findNavController().popBackStack()
                }
            } else if (item is MenuItem) {
                findNavController().navigate(DetailsFragmentDirections.actionDetailsSelf(
                        item.getTitleRes(context!!), item.getIconRes(context!!)))
            }
        }
    }

    inner class DetailsDescriptionPresenter : AbstractDetailsDescriptionPresenter() {
        override fun onBindDescription(
                vh: ViewHolder, item: Any) {
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