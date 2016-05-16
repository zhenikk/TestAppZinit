package eugen.testappzinit.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import eugen.testappzinit.Constants;
import eugen.testappzinit.R;
import eugen.testappzinit.model.BashImageModel;

/**
 * Created by Yevhenii on 16.05.16.
 */
public class DetailActivity extends BaseActivity {
    ImageView mImageView;
    TextView mTextViewDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getActionBar() != null){
            getActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);}
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        mImageView = (ImageView) findViewById(R.id.image);
        mTextViewDescription = (TextView) findViewById(R.id.tvDescription);
        BashImageModel value = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = (BashImageModel) extras.getSerializable(Constants.DETAIL_KEY_PICTURE);
        }
        if (value != null) {
            Picasso.with(this).load(value.getImage()).into(mImageView);
            mTextViewDescription.setText(value.getTitle());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
