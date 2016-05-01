
package idm.tpf.sinai.listeners;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import idm.tpf.sinai.activity.MainActivity;
import idm.tpf.sinai.adapter.ViewPagerAdapter;


/**
 * Created by Mar on 30/04/2016.
 */
public class PageListener implements ViewPager.OnPageChangeListener {

    protected Context mCtx;
    protected ViewPager mViewPager;

    public PageListener(Context ctx,ViewPager viewPager){
        mCtx=ctx;
        mViewPager=viewPager;
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Fragment fragment=((ViewPagerAdapter)mViewPager.getAdapter()).getItem(position);
        if (fragment!=null && position== MainActivity.ID_JobsFragment){
            fragment.onResume();
        }
        Log.v("PageListener", " onPageSelected  :" + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

