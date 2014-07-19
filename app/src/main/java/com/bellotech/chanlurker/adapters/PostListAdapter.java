package com.bellotech.chanlurker.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bellotech.chanlurker.DownloadThumbNailTask;
import com.bellotech.chanlurker.R;
import com.bellotech.chanlurker.data.Post;
import com.bellotech.chanlurker.enums.ImageDimensions;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends ArrayAdapter<Post> {
    private Context context;
    private String board;
    private List<String> tempImgFilePaths;

    public PostListAdapter(Context context, int textViewResourceId, List<Post> items, String board, List<String> tempImgFilePaths) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.board = board;
        this.tempImgFilePaths = tempImgFilePaths;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.posts_selection_layout, null);
        }

        Post post = getItem(position);
        if (post != null) {
            TextView postCommentNo = (TextView) view.findViewById(R.id.postSelectionCommentNo);
            if (postCommentNo != null) {
                postCommentNo.setText(post.getPostNo().toString());
            }
            TextView postDate = (TextView) view.findViewById(R.id.postSelectionDate);
            if (postDate != null) {
                postDate.setText(post.getDate());
            }
            TextView postComment = (TextView) view.findViewById(R.id.postSelectionComment);
            if (postComment != null) {
                postComment.setText(Html.fromHtml(post.getComment()));
            }
            TextView postName = (TextView) view.findViewById(R.id.postSelectionName);
            if (postName != null) {
                postName.setText(post.getName());
            }
            ImageView postThumbNail = (ImageView) view.findViewById(R.id.postThumbNail);
            if(post.getTim() != null && !post.getTim().isEmpty()){
                String imgURL;
                String fileType;
                ImageDimensions dim;
                //if(ConnectionUtils.isWifiConnected(parent.getContext()))
                    fileType = ".jpg";
                    imgURL = "https://t.4cdn.org/" + board + "/" + post.getTim() + "s" + fileType;
                    dim = ImageDimensions.ThreadThumbNail;
                /*else
                fileType = post.getExt();
                imgURL = "https://i.4cdn.org/" + board + "/" + post.getTim() + fileType;*/

                String tempFileName = post.getTim() + fileType;

                new DownloadThumbNailTask(postThumbNail, tempFileName, tempImgFilePaths,
                        parent.getContext(), !tempImgFilePaths.contains(tempFileName), dim).execute(imgURL);
            }else{
                postThumbNail.setVisibility(View.GONE);
            }
        }

        return view;
    }
}
