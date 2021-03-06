package com.tetsoft.typego.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tetsoft.typego.R;
import com.tetsoft.typego.data.language.LanguageSelectable;
import com.tetsoft.typego.game.mode.GameMode;
import com.tetsoft.typego.game.mode.GameOnTime;
import com.tetsoft.typego.game.result.GameResultList;
import com.tetsoft.typego.utils.TimeConvert;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GamesHistoryAdapter extends RecyclerView.Adapter<GamesHistoryAdapter.TestViewHolder> {
    GameResultList results;
    Context context;
    private final RecyclerViewOnClickListener listener;

    public GamesHistoryAdapter(Context context, GameResultList results, RecyclerViewOnClickListener listener) {
        this.context = context;
        this.results = results;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.passed_tests_item, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        Date completionTime = new Date(results.get(position).getCompletionDateTime());
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.DEFAULT, SimpleDateFormat.SHORT);
        holder.itemDateTime.setText(dateFormat.format(completionTime));
        holder.itemWPM.setText(context.getString(R.string.wpm_pl, (int) results.get(position).getWpm()));
        GameMode mode = results.get(position).getGameMode();
        if (mode instanceof GameOnTime) {
            holder.itemTimeInSeconds.setText(TimeConvert.convertSecondsToStamp(((GameOnTime) mode).getTimeMode().getTimeInSeconds()));
        }
        if (mode instanceof LanguageSelectable) {
            holder.itemLanguage.setText(((LanguageSelectable) mode).getLanguage().getIdentifier());
        }
    }

    // TODO: refactor this, so user can select how many items do they want to see.
    @Override
    public int getItemCount() {
        if (results == null) return 0;
        int MAX_ITEM_COUNT = 30;
        return Math.min(results.size(), MAX_ITEM_COUNT);
    }

    public interface RecyclerViewOnClickListener {
        void OnClick(View v, int position);
    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemDateTime;
        TextView itemWPM;
        TextView itemTimeInSeconds;
        TextView itemLanguage;
        ImageView itemImage;

        public TestViewHolder(View itemView) {
            super(itemView);
            itemDateTime = itemView.findViewById(R.id.itemDateTime);
            itemWPM = itemView.findViewById(R.id.itemWPM);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemTimeInSeconds = itemView.findViewById(R.id.itemTimeInSeconds);
            itemLanguage = itemView.findViewById(R.id.itemLanguage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.OnClick(v, getAdapterPosition());
        }
    }

}
