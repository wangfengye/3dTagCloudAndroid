package com.moxun.tagcloud;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;

import com.moxun.tagcloudlib.view.FollowTagCloudView;
import com.moxun.tagcloudlib.view.TagCloudView;

public class MainActivity extends AppCompatActivity {

    private TagCloudView tagCloudView;
    private TextTagsAdapter textTagsAdapter;
    private ViewTagsAdapter viewTagsAdapter;
    private VectorTagsAdapter vectorTagsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tagCloudView = (TagCloudView) findViewById(R.id.tag_cloud);

        textTagsAdapter = new TextTagsAdapter(new String[20]);
        viewTagsAdapter = new ViewTagsAdapter();
        vectorTagsAdapter = new VectorTagsAdapter();

        tagCloudView.setAdapter(textTagsAdapter);

        findViewById(R.id.tag_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(textTagsAdapter);
            }
        });

        findViewById(R.id.tag_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(viewTagsAdapter);
            }
        });

        findViewById(R.id.tag_vector).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagCloudView.setAdapter(vectorTagsAdapter);
            }
        });

        findViewById(R.id.test_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FragmentTestActivity.class));
            }
        });
        AppCompatSpinner spinner = (AppCompatSpinner) findViewById(R.id.test_mode);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 2:
                        tagCloudView.setAutoScrollMode(TagCloudView.MODE_DISABLE);
                        break;
                    case 1:
                        tagCloudView.setAutoScrollMode(TagCloudView.MODE_DECELERATE);
                        break;
                    case 0:
                        tagCloudView.setAutoScrollMode(TagCloudView.MODE_UNIFORM);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
