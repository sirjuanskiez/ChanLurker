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
import com.bellotech.chanlurker.utils.ConnectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ArrayAdapter that is responsible for setting fields of each PostView
 */
public class PostListAdapter extends ArrayAdapter<Post> {
    //Reference to the context
    private Context context;
    //Board that is currently displayed
    private final String board;

    public PostListAdapter(Context context, int textViewResourceId, List<Post> items, String board) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.board = board;
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
            //Sets the post number
            TextView postCommentNo = (TextView) view.findViewById(R.id.postSelectionCommentNo);
            if (postCommentNo != null) {
                postCommentNo.setText(post.getPostNo().toString());
            }
            //Sets the post date, formatted in Mon. Date, Year Time(Military)
            TextView postDate = (TextView) view.findViewById(R.id.postSelectionDate);
            if (postDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy H:mm aaa");
                Date d = new Date(post.getDate());
                String formattedDate = sdf.format(d);
                postDate.setText(formattedDate);
            }
            //Sets the post's text
            TextView postComment = (TextView) view.findViewById(R.id.postSelectionComment);
            if (postComment != null) {
                postComment.setText(Html.fromHtml(post.getComment()));
            }
            //Sets the author of the post
            TextView postName = (TextView) view.findViewById(R.id.postSelectionName);
            if (postName != null) {
                postName.setText(post.getName());
            }
            //Loads the post's image
            ImageView postThumbNail = (ImageView) view.findViewById(R.id.postThumbNail);
            if(post.getTim() != null && !post.getTim().isEmpty()) {
                String imgURL;
                String fileType;
                ImageDimensions dim;
                boolean isFullImgDl = ConnectionUtils.isWifiConnected(parent.getContext());
                if(!isFullImgDl){
                    fileType = ".jpg";
                    imgURL = "https://t.4cdn.org/" + board + "/" + post.getTim() + "s" + fileType;
                    dim = ImageDimensions.ThreadThumbNail;
                }else{
                    fileType = post.getExt();
                    imgURL = "https://i.4cdn.org/" + board + "/" + post.getTim() + fileType;
                    dim = ImageDimensions.FullSize;
                }

                String tempFileName = (isFullImgDl ? "F" : "T") + post.getTim() + fileType;

                new DownloadThumbNailTask(postThumbNail, tempFileName, parent.getContext(), dim).execute(imgURL);
            }else{
                postThumbNail.setVisibility(View.GONE);
            }
        }

        return view;
    }
}
