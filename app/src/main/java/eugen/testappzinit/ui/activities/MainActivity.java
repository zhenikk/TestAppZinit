package eugen.testappzinit.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import eugen.testappzinit.Constants;
import eugen.testappzinit.R;
import eugen.testappzinit.contract.PicturesContract;
import eugen.testappzinit.model.BashImageModel;
import eugen.testappzinit.presentation.PresenterMainActivity;
import eugen.testappzinit.ui.custom.DividerItemDecoration;
import eugen.testappzinit.ui.custom.EndlessRecyclerViewScrollListener;

public class MainActivity extends BaseActivity implements PicturesContract.View {

    private PicturesContract.Presenter mPresenter;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new PresenterMainActivity(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvPicturesList);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mPresenter.loadDataFromServer(true);
            }
        });

        mRecyclerView.setAdapter(mPresenter.getAdapter());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                mPresenter.loadMore(page);
            }
        });


        mPresenter.loadDataFromServer(true);

    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void showError(String localizedMessage) {
        Toast.makeText(this, localizedMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPopup(final BashImageModel bashImageModel) {
        String date = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.US).format(new Date());

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.test_toast_layout,
                (ViewGroup) findViewById(R.id.linearLayoutToast));

        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        TextView textView = (TextView) view.findViewById(R.id.tvDescription);
        TextView textViewTime = (TextView) view.findViewById(R.id.tvTime);

        Picasso.with(this).load(bashImageModel.getImage()).into(iv);
        if (bashImageModel.getTitle().equals("")) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(bashImageModel.getTitle());
        }
        textViewTime.setText(date);

        Toast toast = new Toast(this);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void showDetailed(BashImageModel bashImageModel) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.DETAIL_KEY_PICTURE, bashImageModel);
        startActivity(intent);
    }

    @Override
    public void stopLoading() {
        swipeContainer.setRefreshing(false);
    }
}
