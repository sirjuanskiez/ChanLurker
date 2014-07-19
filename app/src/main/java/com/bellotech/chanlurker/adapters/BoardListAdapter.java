package com.bellotech.chanlurker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bellotech.chanlurker.R;
import com.bellotech.chanlurker.data.Board;

import java.util.List;

/**
 * Created by Juan on 7/18/2014.
 */
public class BoardListAdapter extends ArrayAdapter<Board> {
    private Context context;

    public BoardListAdapter(Context context, int textViewResourceId, List<Board> items) {
        super(context, textViewResourceId, items);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.boards_selection_layout, null);
        }

        Board board = getItem(position);
        if (board!= null) {
            TextView boardName = (TextView) view.findViewById(R.id.boardSelectionName);
            if (boardName != null) {
                boardName.setText(board.getName());
            }
            TextView boardTitle = (TextView) view.findViewById(R.id.boardSelectionTitle);
            if (boardTitle != null) {
                boardTitle.setText(board.getTitle());
            }
            TextView boardWorkSafe = (TextView) view.findViewById(R.id.boardSelectionWorkSafe);
            if (boardWorkSafe != null) {
                boardWorkSafe.setText("Is work-safe: " + (board.getWorkSafe() == 1 ? "Y" : "N"));
            }
            TextView boardMaxThreads = (TextView) view.findViewById(R.id.boardSelectionThreadsPerPage);
            if (boardMaxThreads != null) {
                boardMaxThreads.setText("Threads per page: " + board.getThreadsPerPage());
            }
            TextView boardMaxPages = (TextView) view.findViewById(R.id.boardSelectionMaxPages);
            if (boardMaxPages != null) {
                boardMaxPages.setText("Max pages: " + board.getMaxPages());
            }
        }

        return view;
    }
}
