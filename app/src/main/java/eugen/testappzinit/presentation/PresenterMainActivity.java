package eugen.testappzinit.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import eugen.testappzinit.Constants;
import eugen.testappzinit.api.PicturesService;
import eugen.testappzinit.contract.BaseContract;
import eugen.testappzinit.contract.PicturesContract;
import eugen.testappzinit.model.BashImageModel;
import eugen.testappzinit.ui.activities.DetailActivity;
import eugen.testappzinit.ui.adapters.MainListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yevhenii on 16.05.16.
 */
public class PresenterMainActivity implements PicturesContract.Presenter {
    private static final String TAG = "PresenterMainActivity";
    private final static int INTERVAL = 1000 * 60 * 2; //2 minutes
    private int mStartIndex;
    private PicturesContract.View mView;
    private MainListAdapter mAdapter;
    private List<BashImageModel> mList;
    private PicturesService service = new PicturesService();
    private Random r = new Random();
    private Handler mHandler = new Handler();
    private Runnable mHandlerTask = new Runnable() {
        @Override
        public void run() {
            showPopup();
            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };

    public PresenterMainActivity(PicturesContract.View mView) {
        attachView(mView);
        mList = new ArrayList<>();
        mStartIndex = 0;
        createAdapter();
    }


    @Override
    public void attachView(PicturesContract.View view) {
        mView = view;
        startRepeatingTask();
    }

    @Override
    public void detachView() {
        stopRepeatingTask();
    }

    @Override
    public void loadDataFromServer() {

        Call<List<BashImageModel>> call = service.getApi().getBestPictures(mStartIndex, Constants.PER_PAGE_IMAGES_LIMIT);
        call.enqueue(new Callback<List<BashImageModel>>() {

            @Override
            public void onResponse(Call<List<BashImageModel>> call, Response<List<BashImageModel>> response) {
                int tempSize = mList.size() - 1;
                mList.addAll(response.body());
                mAdapter.notifyItemRangeInserted(tempSize, response.body().size());

            }

            @Override
            public void onFailure(Call<List<BashImageModel>> call, Throwable t) {
                mView.showError(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public MainListAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void loadMore(int page) {
        mStartIndex = page * Constants.PER_PAGE_IMAGES_LIMIT;
        loadDataFromServer();

    }

    private void createAdapter() {
        mAdapter = new MainListAdapter(mList, mView.getContext());
        mAdapter.setOnItemClickListener(new MainListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //show activity
                mView.showDetailed(mList.get(position));

            }
        });
    }

    void startRepeatingTask() {
        mHandlerTask.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mHandlerTask);
    }

    void showPopup() {
        if (mList != null) {
            int pos = r.nextInt(mList.size() - 1);
            mView.showPopup(mList.get(pos));
        }
    }
}
