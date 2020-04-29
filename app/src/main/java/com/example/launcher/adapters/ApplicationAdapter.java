package com.example.launcher.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.launcher.R;
import com.example.launcher.models.Application;
import java.util.ArrayList;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {
    private List<Application> _appsList;

    public ApplicationAdapter(Context c) {
        _appsList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder viewHolder, int i) {

        String appLabel = _appsList.get(i).getLabel().toString();
        String appPackage = _appsList.get(i).getPackageName().toString();
        Drawable appIcon = _appsList.get(i).getIcon();

        TextView textView = viewHolder.textView;
        textView.setText(appLabel);
        ImageView imageView = viewHolder.img;
        imageView.setImageDrawable(appIcon);

    }

    @Override
    public int getItemCount() {
        return _appsList.size();
    }


    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.application_row, parent, false);
        ApplicationViewHolder viewHolder = new ApplicationViewHolder(view);
        return viewHolder;
    }

    public void addApp(Application app) {
        _appsList.add(app);
    }


    public class ApplicationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ConstraintLayout row;
        public TextView textView;
        public ImageView img;

        public ApplicationViewHolder(View itemView) {
            super(itemView);

            row = itemView.findViewById(R.id.a_row);
            textView = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Context context = v.getContext();

            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(_appsList.get(pos).getPackageName().toString());
            context.startActivity(launchIntent);
            Toast.makeText(v.getContext(), _appsList.get(pos).getLabel().toString(), Toast.LENGTH_LONG).show();
        }
    }

}

