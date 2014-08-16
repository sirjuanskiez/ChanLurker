package com.bellotech.chanlurker;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.bellotech.chanlurker.adapters.PostListAdapter;
import com.bellotech.chanlurker.data.Post;
import com.bellotech.chanlurker.parsers.PostListLoader;

import java.util.List;

public class SingleThreadActivity extends Activity {
    private ListView postList;
    private String board;
    private String threadNo;
    private String scrollToPostNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_thread);
        Intent i = getIntent();
        board = i.getStringExtra(HomeActivity.BOARD_TO_LOAD);
        threadNo = i.getStringExtra(ThreadsActivity.THREAD_TO_LOAD);
        scrollToPostNo = i.getStringExtra(ThreadsActivity.COMMENT_TO_SCROLL_TO);
        initPostList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.single_thread, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            doPostListLoad();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPostList(){
        postList = (ListView)findViewById(R.id.postList);
        doPostListLoad();
    }

    private void doPostListLoad(){
        new PostListLoaderTask().execute(board, threadNo, scrollToPostNo);
    }

    private class PostListLoaderTask extends AsyncTask<String,String,List<Post>> {

        private List<Post> posts;
        private String board;
        private String scrollToPostNo;

        @Override
        protected List<Post> doInBackground(String... params) {
            board = params[0];
            scrollToPostNo = params[2];
            posts = PostListLoader.LoadPostList(params);
            return posts;
        }

        @Override
        protected void onPostExecute(List<Post> result) {
            if(result != null) {
                postList.setAdapter(new PostListAdapter(getApplicationContext(), R.layout.posts_selection_layout, result, board));
                postList.setScrollingCacheEnabled(true);
                if(scrollToPostNo != null && !scrollToPostNo.isEmpty()){
                    for(Post p : result){
                        if (p.getPostNo().toString().equals(scrollToPostNo)){
                            postList.setSelection(result.indexOf(p));
                            break;
                        }
                    }
                }
            }else
                Toast.makeText(getApplicationContext(), "Failed to load list of posts", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onProgressUpdate(String... text) {

        }
    }
}
