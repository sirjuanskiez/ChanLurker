package com.bellotech.chanlurker;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bellotech.chanlurker.adapters.ThreadListAdapter;
import com.bellotech.chanlurker.data.Thread;
import com.bellotech.chanlurker.parsers.ThreadListLoader;

import java.util.List;

public class ThreadsActivity extends Activity {
    public static final String THREAD_TO_LOAD = "com.bellotech.chanlurker.THREAD_TO_LOAD";
    public static final String COMMENT_TO_SCROLL_TO = "com.bellotech.chanlurker.COMMENT_TO_SCROLL_TO";
    private ListView threadList;
    private String board;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        Intent i = getIntent();
        board = i.getStringExtra(HomeActivity.BOARD_TO_LOAD);
        setTitle(i.getStringExtra(HomeActivity.BOARD_TITLE));
        if(!board.isEmpty()) {
            board = board.replace("/", "");
        }
        initThreadList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.threads, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            doThreadListLoad(1 + "");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initThreadList(){
        threadList = (ListView)findViewById(R.id.threadList);
        threadList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView threadNo = (TextView) view.findViewById(R.id.threadSelectionPostNo);
                if (threadNo != null) {
                    Intent intent = new Intent(getBaseContext(), SingleThreadActivity.class);
                    intent.putExtra(THREAD_TO_LOAD, threadNo.getText());
                    intent.putExtra(COMMENT_TO_SCROLL_TO, "");
                    intent.putExtra(HomeActivity.BOARD_TO_LOAD, board);
                    startActivity(intent);
                }
            }
        });
        doThreadListLoad(1 + "");
    }

    private void doThreadListLoad(String page){
        new ThreadListLoaderTask().execute(board, page);
    }

    private class ThreadListLoaderTask extends AsyncTask<String,String,List<Thread>> {
        private List<Thread> threads;
        private String board;

        @Override
        protected List<Thread> doInBackground(String... params) {
            this.board = params[0];
            threads = ThreadListLoader.LoadThreadList(params);
            return threads;
        }

        @Override
        protected void onPostExecute(List<Thread> result) {
            if(result != null) {
                threadList.setAdapter(new ThreadListAdapter(getApplicationContext(), R.layout.threads_selection_layout, result, board));
                threadList.setScrollingCacheEnabled(true);
            }else
                Toast.makeText(getApplicationContext(), "Failed to load list of threads", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onProgressUpdate(String... text) {

        }
    }
}
