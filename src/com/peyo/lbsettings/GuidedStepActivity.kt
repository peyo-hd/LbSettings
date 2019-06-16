package com.peyo.lbsettings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.GuidedStepSupportFragment
import androidx.leanback.widget.GuidanceStylist
import androidx.leanback.widget.GuidedAction
import androidx.leanback.widget.GuidedActionsStylist

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
                    description, "Menu Item Guided Step",
                    resources.getDrawable(iconRes, null))
        }

        private val mAction = GuidedAction.Builder(context).title("EDIT")
                .editable(true).build()

        override fun onCreateActions(actions: MutableList<GuidedAction>,
                                     savedInstanceState: Bundle?) {
            actions.add(mAction)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            openInEditMode(mAction)
        }

        override fun onCreateActionsStylist(): GuidedActionsStylist {
            return object : GuidedActionsStylist() {
                override fun onProvideItemLayoutId(): Int {
                    return R.layout.text_input
                }
            }
        }

        override fun onGuidedActionClicked(action: GuidedAction?) {
            activity!!.finish()
        }

        override fun onGuidedActionEditedAndProceed(action: GuidedAction?): Long {
            return GuidedAction.ACTION_ID_FINISH
        }
    }

}
