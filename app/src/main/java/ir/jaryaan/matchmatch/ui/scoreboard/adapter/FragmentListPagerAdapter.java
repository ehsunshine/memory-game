package ir.jaryaan.matchmatch.ui.scoreboard.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ir.jaryaan.matchmatch.ui.leaderboard.LeaderboardFragment;

/**
 * Created by ehsun on 5/23/2017.
 */

public class FragmentListPagerAdapter extends FragmentPagerAdapter {

    private String scoreID;

    public FragmentListPagerAdapter(FragmentManager fragmentManager, @NonNull String scoreID) {
        super(fragmentManager);
        this.scoreID = scoreID;
    }

    @Override
    public Fragment getItem(int position) {
        return LeaderboardFragment.newInstance(position, scoreID);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Easy";
            case 1:
                return "Normal";
            case 2:
                return "Hard";
            case 3:
                return "Insane";
        }
        return null;
    }
}