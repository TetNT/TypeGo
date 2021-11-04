package com.tetsoft.typego.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tetsoft.typego.BuildConfig;
import com.tetsoft.typego.R;

import org.jetbrains.annotations.NotNull;

public class ReleaseNotesFragment extends Fragment {


    public ReleaseNotesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_release_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TextView tvNotesHeader = view.findViewById(R.id.tv_whats_new);
        Context context = getContext();
        if (context != null)
            tvNotesHeader.setText(context.getString(R.string.whats_new_pl, BuildConfig.VERSION_NAME));
    }
}