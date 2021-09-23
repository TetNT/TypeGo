package com.tetsoft.typego.testing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tetsoft.typego.R;
import com.tetsoft.typego.utils.TimeConvert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PassedTestsAdapter extends RecyclerView.Adapter<PassedTestsAdapter.TestViewHolder> {
    ArrayList<TypingResult> results;
    Context context;
    private final RecyclerViewOnClickListener listener;

    public PassedTestsAdapter(Context context, ArrayList<TypingResult> results, RecyclerViewOnClickListener listener) {
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
        Date completionTime = results.get(position).getCompletionDateTime();
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.DEFAULT, SimpleDateFormat.SHORT);
        holder.itemDateTime.setText(dateFormat.format(completionTime));
        holder.itemWPM.setText(context.getString(R.string.wpm_pl, (int)results.get(position).getWPM()));
        holder.itemTimeInSeconds.setText(TimeConvert.convertSecondsToStamp(results.get(position).getTimeInSeconds()));
        holder.itemLanguage.setText(results.get(position).getLanguage().getIdentifier());
    }

    @Override
    public int getItemCount() {
        if (results==null) return 0;
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
