package com.bellotech.chanlurker.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bellotech.chanlurker.DownloadThumbNailTask;
import com.bellotech.chanlurker.HomeActivity;
import com.bellotech.chanlurker.R;
import com.bellotech.chanlurker.SingleThreadActivity;
import com.bellotech.chanlurker.ThreadsActivity;
import com.bellotech.chanlurker.data.Post;
import com.bellotech.chanlurker.data.Thread;
import com.bellotech.chanlurker.enums.ImageDimensions;

import java.util.List;

/**
 * Created by Juan on 7/18/2014.
 */
public class ThreadListAdapter extends ArrayAdapter<Thread> {
    private Context context;
    private String board;
    private List<String> tempImgFilePaths;

    public ThreadListAdapter(Context context, int textViewResourceId, List<Thread> items, String board, List<String> tempImgFilePaths) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.board = board;
        this.tempImgFilePaths = tempImgFilePaths;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.threads_selection_layout, null);

        Thread thread = getItem(position);
        if (thread != null) {
            String postNo = null;
            LinearLayout curView = (LinearLayout)view;
            for (int i = 0; i < thread.getPosts().size(); i++) {
                Post p = thread.getPosts().get(i);
                if(i==0) {
                    TextView threadPostNo = (TextView) view.findViewById(R.id.threadSelectionPostNo);
                    if (threadPostNo != null) {
                        postNo = p.getPostNo().toString();
                        threadPostNo.setText(postNo);
                    }
                    TextView threadDate = (TextView) view.findViewById(R.id.threadSelectionDate);
                    if (threadDate != null) {
                        threadDate.setText(p.getDate());
                    }
                    TextView threadComment = (TextView) view.findViewById(R.id.threadSelectionComment);
                    if (threadComment != null) {
                        threadComment.setText(Html.fromHtml(p.getComment()));
                    }
                    TextView threadName = (TextView) view.findViewById(R.id.threadSelectionName);
                    if (threadName != null) {
                        threadName.setText(p.getName());
                    }
                    ImageView postThumbNail = (ImageView) view.findViewById(R.id.threadThumbNail);
                    if (p.getTim() != null && !p.getTim().isEmpty()) {
                        String imgURL = "https://t.4cdn.org/" + board + "/" + p.getTim() + "s.jpg";
                        String tempFileName = p.getTim() + ".jpg";
                        ImageDimensions dim = ImageDimensions.ThreadThumbNail;

                        new DownloadThumbNailTask(postThumbNail, tempFileName, tempImgFilePaths,
                                parent.getContext(), !tempImgFilePaths.contains(tempFileName), dim).execute(imgURL);
                    } else {
                        postThumbNail.setVisibility(View.GONE);
                        postThumbNail.setOnClickListener(null);
                    }
                }else{
                    LayoutInflater childInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View replyView = childInflater.inflate(R.layout.threads_selection_layout, null);
                    View divider = childInflater.inflate(R.layout.list_divider, null);
                    TextView postCommentNo = (TextView) replyView.findViewById(R.id.threadSelectionPostNo);
                    if (postCommentNo != null) {
                        postCommentNo.setText(p.getPostNo().toString());
                        postCommentNo.setVisibility(View.GONE);
                    }
                    TextView postDate = (TextView) replyView.findViewById(R.id.threadSelectionDate);
                    if (postDate != null) {
                        postDate.setVisibility(View.GONE);
                    }
                    TextView postComment = (TextView) replyView.findViewById(R.id.threadSelectionComment);
                    if (postComment != null) {
                        postComment.setText(Html.fromHtml(p.getComment()));
                    }
                    TextView postName = (TextView) replyView.findViewById(R.id.threadSelectionName);
                    if (postName != null) {
                        postName.setVisibility(View.GONE);
                    }
                    ImageView postThumbNail = (ImageView) replyView.findViewById(R.id.threadThumbNail);
                    if(p.getTim() != null && !p.getTim().isEmpty()){
                        String imgURL = "https://t.4cdn.org/" + board + "/" + p.getTim() + "s.jpg";
                        String tempFileName = p.getTim() + ".jpg";
                        ImageDimensions dim = ImageDimensions.ThreadScaled;

                        new DownloadThumbNailTask(postThumbNail, tempFileName, tempImgFilePaths,
                                parent.getContext(), !tempImgFilePaths.contains(tempFileName), dim).execute(imgURL);
                    }else{
                        postThumbNail.setVisibility(View.GONE);
                    }

                    replyView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LinearLayout selectedView = (LinearLayout) v;
                            LinearLayout parentView = (LinearLayout) selectedView.getParent();
                            TextView opNo = (TextView) parentView.findViewById(R.id.threadSelectionPostNo);
                            TextView postCommentNo = (TextView) selectedView.findViewById(R.id.threadSelectionPostNo);
                            Intent intent = new Intent(v.getContext(), SingleThreadActivity.class);
                            intent.putExtra(ThreadsActivity.THREAD_TO_LOAD, opNo.getText());
                            intent.putExtra(ThreadsActivity.COMMENT_TO_SCROLL_TO, postCommentNo.getText());
                            intent.putExtra(HomeActivity.BOARD_TO_LOAD, board);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            v.getContext().startActivity(intent);
                        }
                    });

                    curView.addView(divider);
                    curView.addView(replyView);
                    p.setOpNo(postNo);
                }
            }
        }

        return view;
    }
}
