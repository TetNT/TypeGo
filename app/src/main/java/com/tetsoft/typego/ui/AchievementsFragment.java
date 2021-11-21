package com.tetsoft.typego.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tetsoft.typego.account.User;
import com.tetsoft.typego.achievement.Achievement;
import com.tetsoft.typego.achievement.AchievementsAdapter;
import com.tetsoft.typego.databinding.FragmentAchievementsBinding;

import java.util.ArrayList;

public class AchievementsFragment extends Fragment {


    FragmentAchievementsBinding _binding;

    private @NonNull FragmentAchievementsBinding getBinding() {
        return _binding;
    }

    public AchievementsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _binding = FragmentAchievementsBinding.inflate(inflater, container, false);
        return getBinding().getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        _binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        User currentUser = User.getFromStoredData(getContext());
        ArrayList<Achievement> staticAchievements = Achievement.getAchievementList(getContext());
        getBinding().rvAchievements.setAdapter(new AchievementsAdapter(
                getContext(),
                staticAchievements,
                currentUser));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        getBinding().rvAchievements.setLayoutManager(linearLayoutManager);
    }
}