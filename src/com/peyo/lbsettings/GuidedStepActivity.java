package com.peyo.lbsettings;

import android.app.Activity;
import android.os.Bundle;
import androidx.leanback.app.GuidedStepFragment;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;

import java.util.List;

public class GuidedStepActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        GuideFragment.addAsRoot(this, new GuideFragment(), R.id.guidedstep);
    }

    public static class GuideFragment extends GuidedStepFragment {
        @Override
        public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
            int titleRes = getActivity().getIntent().getIntExtra("TitleRes", 0);
            int iconRes = getActivity().getIntent().getIntExtra("IconRes", 0);
            String description = "Lorem ipsum dolor sit amet, consectetur "
                    + "adipisicing elit, sed do eiusmod tempor incididunt ut labore "
                    + " et dolore magna aliqua. Ut enim ad minim veniam, quis "
                    + "nostrud exercitation ullamco laboris nisi ut aliquip ex ea "
                    + "commodo consequat.";
            return new GuidanceStylist.Guidance(getResources().getString(titleRes),
                    description, "Menu Item Guided Step", getResources().getDrawable(iconRes));
        }

        @Override
        public void onCreateActions(List<GuidedAction> actions, Bundle savedInstanceState) {
            actions.add(new GuidedAction.Builder(getContext()).title("OK").build());
            actions.add(new GuidedAction.Builder(getContext()).title("Cancel").build());
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            getActivity().finish();
        }
    }

}
