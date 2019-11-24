package com.peyo.lbsettings

import android.os.Bundle
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import androidx.navigation.fragment.findNavController

class GuidedFragment : GuidedStepSupportFragment() {
    override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
        val args = GuidedFragmentArgs.fromBundle(arguments!!)
        val titleRes = args.titleResource
        val iconRes = args.iconResource

        val description = ("Lorem ipsum dolor sit amet, consectetur "
                + "adipisicing elit, sed do eiusmod tempor incididunt ut labore "
                + " et dolore magna aliqua. Ut enim ad minim veniam, quis "
                + "nostrud exercitation ullamco laboris nisi ut aliquip ex ea "
                + "commodo consequat.")
        return GuidanceStylist.Guidance(resources.getString(titleRes),
                description, "Menu Item Guided Step", resources.getDrawable(iconRes, null))
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
        actions.add(GuidedAction.Builder(context).title("OK").id(1L).build())
        actions.add(GuidedAction.Builder(context).title("Cancel").id(2L).build())
    }

    override fun onGuidedActionClicked(action: GuidedAction?) {
        if (action!!.id == 1L) {
            findNavController().popBackStack(R.id.menu_fragment, false)
        } else {
            findNavController().popBackStack()
        }
    }
}