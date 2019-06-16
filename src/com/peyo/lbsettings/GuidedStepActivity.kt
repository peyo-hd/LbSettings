package com.peyo.lbsettings

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction

class GuidedStepActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)
        GuidedStepSupportFragment.addAsRoot(this, GuideFragment(), R.id.guidedstep)
    }

    class GuideFragment : GuidedStepSupportFragment() {
        override fun onCreateGuidance(savedInstanceState: Bundle?): GuidanceStylist.Guidance {
            val titleRes = activity!!.intent.getIntExtra("TitleRes", 0)
            val iconRes = activity!!.intent.getIntExtra("IconRes", 0)
            val description = ("Lorem ipsum dolor sit amet, consectetur "
                    + "adipisicing elit, sed do eiusmod tempor incididunt ut labore "
                    + " et dolore magna aliqua. Ut enim ad minim veniam, quis "
                    + "nostrud exercitation ullamco laboris nisi ut aliquip ex ea "
                    + "commodo consequat.")
            return GuidanceStylist.Guidance(resources.getString(titleRes),
                    description, "Menu Item Guided Step", resources.getDrawable(iconRes, null))
        }

        override fun onCreateActions(actions: MutableList<GuidedAction>, savedInstanceState: Bundle?) {
            actions.add(GuidedAction.Builder(context).title("OK").build())
            actions.add(GuidedAction.Builder(context).title("Cancel").build())
        }

        override fun onGuidedActionClicked(action: GuidedAction?) {
            activity!!.finish()
        }
    }

}
