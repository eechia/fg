package com.feedgeorge.android;

import android.content.Intent;
import android.os.Bundle;

public class TabGroup1Activity extends TabGroupActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startChildActivity("PostList", new Intent(this,PostList.class));
    }
}
