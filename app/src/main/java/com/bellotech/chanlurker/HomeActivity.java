package com.bellotech.chanlurker;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bellotech.chanlurker.adapters.BoardListAdapter;
import com.bellotech.chanlurker.cache.ImageFilePathsCache;
import com.bellotech.chanlurker.data.Board;
import com.bellotech.chanlurker.parsers.BoardListLoader;

import java.io.File;
import java.util.List;


public class HomeActivity extends Activity {
    public static final String BOARD_TO_LOAD = "com.bellotech.chanlurker.BOARD_TO_LOAD";
    public static final String BOARD_TITLE= "com.bellotech.chanlurker.BOARD_TITLE";
    public static final String TAG = "HomeActivity";
    private ListView boardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initBoardList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initBoardList(){
        boardList = (ListView)findViewById(R.id.boardList);
        boardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView boardName = (TextView) view.findViewById(R.id.boardSelectionName);
                TextView boardDesc = (TextView) view.findViewById(R.id.boardSelectionTitle);
                if (boardName != null) {
                    Intent intent = new Intent(getBaseContext(), ThreadsActivity.class);
                    intent.putExtra(BOARD_TO_LOAD, boardName.getText());
                    intent.putExtra(BOARD_TITLE, boardName.getText() + " - " + boardDesc.getText());
                    startActivity(intent);
                }
            }
        });
        doBoardListLoad();
    }

    private void doBoardListLoad(){
        new BoardListLoaderTask().execute();
    }

    private class BoardListLoaderTask extends AsyncTask<String,String,List<Board>> {
        @Override
        protected List<Board> doInBackground(String... params) {
            return BoardListLoader.LoadBoardList();
        }

        @Override
        protected void onPostExecute(List<Board> result) {
            if(result != null)
                boardList.setAdapter(new BoardListAdapter(getApplicationContext(), R.layout.boards_selection_layout, result));
            else
                Toast.makeText(getApplicationContext(), "Failed to load list of boards", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {

        }
        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    protected void onDestroy(){
        try {
            File cacheDir = getFilesDir();
            if (cacheDir != null) {
                String fList[] = cacheDir.list();
                for (String filePath : fList) {
                    File file = new File(cacheDir, filePath);
                    file.delete();
                }
            }
            ImageFilePathsCache.purgeCache();
        }catch (Exception e){
            Log.v(TAG, "Failed to clear image cache", e);
        }
        super.onDestroy();
    }
}
