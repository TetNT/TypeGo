package com.tetsoft.typego;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tetsoft.typego.adapters.AchievementsAdapter;
import com.tetsoft.typego.utils.Achievement;
import com.tetsoft.typego.utils.User;
import java.util.ArrayList;

public class AchievementsFragment extends Fragment {


    public AchievementsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_achievements, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User currentUser = User.getFromStoredData(getContext());
        ArrayList<Achievement> staticAchievements = Achievement.getAchievementList(getContext());
        RecyclerView rvAchievements = view.findViewById(R.id.rv_achievements);
        rvAchievements.setAdapter(new AchievementsAdapter(
                getContext(),
                staticAchievements,
                currentUser));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvAchievements.setLayoutManager(linearLayoutManager);
    }
}