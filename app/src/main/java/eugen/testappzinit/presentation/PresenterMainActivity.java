package eugen.testappzinit.presentation;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eugen.testappzinit.api.PicturesService;
import eugen.testappzinit.contract.BaseContract;
import eugen.testappzinit.contract.PicturesContract;
import eugen.testappzinit.model.BashImageModel;
import eugen.testappzinit.ui.adapters.MainListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yevhenii on 16.05.16.
 */
public class PresenterMainActivity implements PicturesContract.Presenter {
    private static final String TAG = "PresenterMainActivity";
    private MainListAdapter mAdapter;
    private List<BashImageModel> mList;

    private PicturesContract.View mView;

    public PresenterMainActivity(PicturesContract.View mView) {
        attachView(mView);
        mList = new ArrayList<>();
        createAdapter();
    }


    @Override
    public void attachView(PicturesContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void loadDataFromServer() {
        PicturesService service = new PicturesService();

        Call<List<BashImageModel>> call = service.getApi().getBestPictures();
        call.enqueue(new Callback<List<BashImageModel>>() {

            @Override
            public void onResponse(Call<List<BashImageModel>> call, Response<List<BashImageModel>> response) {
                mList.clear();
                mList.addAll(response.body());
                mAdapter.notifyDataSetChanged();

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

    private void createAdapter() {
        mAdapter = new MainListAdapter(mList, mView.getContext());
    }
}
