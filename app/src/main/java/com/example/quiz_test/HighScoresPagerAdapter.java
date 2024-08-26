package com.example.quiz_test;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class HighScoresPagerAdapter extends FragmentStateAdapter {

    public HighScoresPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return HighScoresFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Easy";
            case 1: return "Medium";
            case 2: return "Hard";
            default: return null;
        }
    }
}
