package com.example.typego.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.typego.R;
import com.example.typego.utils.TypingResult;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class PassedTestsAdapter extends RecyclerView.Adapter<PassedTestsAdapter.TestViewHolder> {
    ArrayList<TypingResult> results;
    public final int PREV_RES_IMAGE = R.drawable.ic_prev_res;
    Context context;
    private RecyclerViewOnClickListener listener;

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
        holder.itemImage.setImageResource(PREV_RES_IMAGE);
        Date completionTime = results.get(position).getCompletionDateTime();
        DateFormat dateFormat = SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.DEFAULT, SimpleDateFormat.SHORT);
        //DateFormat timeFormat = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT, context.getResources().getConfiguration().locale);
        //holder.itemDateTime.setText(dateFormat.format(completionTime) + " " + timeFormat.format(completionTime));
        holder.itemDateTime.setText(dateFormat.format(completionTime));
        holder.itemWPM.setText(context.getText(R.string.WPM) + ": " + (int)results.get(position).getWPM());
    }

    @Override
    public int getItemCount() {
        if (results!=null)
        return results.size();
        return 0;
    }

    public interface RecyclerViewOnClickListener {
        void OnClick(View v, int position);

    }

    class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemDateTime;
        TextView itemWPM;
        ImageView itemImage;

        public TestViewHolder(View itemView) {
            super(itemView);

            itemDateTime = itemView.findViewById(R.id.itemDateTime);
            itemWPM = itemView.findViewById(R.id.itemWPM);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.OnClick(v, getAdapterPosition());
        }
    }

}
