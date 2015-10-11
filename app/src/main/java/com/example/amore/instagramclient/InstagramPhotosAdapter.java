package com.example.amore.instagramclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by amore on 10/10/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfile);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        TextView tvComment = (TextView) convertView.findViewById(R.id.tvComment);
        TextView tvCommentUser = (TextView) convertView.findViewById(R.id.tvCommentUser);

        tvCaption.setText(photo.caption);
        tvUsername.setText(photo.username);
        tvLikes.setText("\u2665" + photo.likes + " likes");
        tvComment.setText(photo.latestComment);
        tvCommentUser.setText(photo.latestCommentUser + ": ");

        ivPhoto.setImageResource(0);
        ivProfile.setImageResource(0);

        Picasso.with(getContext()).load(photo.imageUrl).into(ivPhoto);
        Picasso.with(getContext()).load(photo.userProfileUrl).into(ivProfile);

        return convertView;
    }
}
