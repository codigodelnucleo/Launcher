package com.example.launcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.launcher.adapters.ApplicationAdapter;
import com.example.launcher.models.Application;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ApplicationAdapter _appsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new myThread().execute();


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RView);
        _appsAdapter = new ApplicationAdapter(this);
        recyclerView.setAdapter(_appsAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

    }

    public void updateView() {
        _appsAdapter.notifyItemInserted(_appsAdapter.getItemCount() - 1);

    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();

    }*/

    public class myThread extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... Params) {

            PackageManager pm = getPackageManager();
            //appsList = new ArrayList<>();

            Intent i = new Intent(Intent.ACTION_MAIN, null);
            i.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> allApps = pm.queryIntentActivities(i, 0);
            for (ResolveInfo ri : allApps) {
                Application app = new Application();
                app.setLabel(ri.loadLabel(pm));
                app.setPackageName(ri.activityInfo.packageName);
                app.setIcon(ri.activityInfo.loadIcon(pm));
                _appsAdapter.addApp(app);
            }
            return "Success";

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            updateView();
        }

    }
}
