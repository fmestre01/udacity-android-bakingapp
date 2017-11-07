package udacity.com.bakingapp.util;

import android.support.v4.app.FragmentManager;

public class FragmentUtil {

    public static void popBackStack(FragmentManager manager) {
        FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
        manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
