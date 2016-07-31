package com.gamik.pastify.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.gamik.pastify.R;

public class UserAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.user_account_toolbar);
        setSupportActionBar(toolbar);
//        int[] image = {R.drawable.ic_delete_database, R.drawable.ic_download_2, R.drawable.ic_synchronize, R.drawable.ic_database_filled};
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.user_account_list);
//        ArrayList<UserAccountOption> list = new ArrayList<>();
//        UserAccountOption userAccountOption = new UserAccountOption(image[0], "Wipe all data");
//        UserAccountOption userAccountOption1 = new UserAccountOption(image[1], "Restore data");
//        UserAccountOption userAccountOption2 = new UserAccountOption(image[2], "Last Sync 12/01/2016 12:30");
//        UserAccountOption userAccountOption3 = new UserAccountOption(image[3], "Total data 205");
//        list.add(userAccountOption);
//        list.add(userAccountOption1);
//        list.add(userAccountOption2);
//        list.add(userAccountOption3);
//        UserAccountAdapter userAccountAdapter = new UserAccountAdapter(list, this);
//        recyclerView.setAdapter(userAccountAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new SmartDecorator(this));

    }
}
