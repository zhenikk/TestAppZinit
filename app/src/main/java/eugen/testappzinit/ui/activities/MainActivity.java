package eugen.testappzinit.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import eugen.testappzinit.R;
import eugen.testappzinit.contract.PicturesContract;
import eugen.testappzinit.presentation.PresenterMainActivity;

public class MainActivity extends BaseActivity implements PicturesContract.View {

    private PicturesContract.Presenter mPresenter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new PresenterMainActivity(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvPicturesList);

        mRecyclerView.setAdapter(mPresenter.getAdapter());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mPresenter.loadDataFromServer();

    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void showError(String localizedMessage) {
        Toast.makeText(this, localizedMessage, Toast.LENGTH_SHORT).show();
    }
}
