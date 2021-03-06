package eugen.testappzinit.contract;

import android.support.v7.widget.RecyclerView;

import java.util.List;

import eugen.testappzinit.model.BashImageModel;
import eugen.testappzinit.ui.adapters.MainListAdapter;

/**
 * Created by Yevhenii on 16.05.16.
 */
public interface PicturesContract extends BaseContract {
    interface View extends BaseContract.View {

        void showError(String localizedMessage);

        void showPopup(BashImageModel bashImageModel);

        void showDetailed(BashImageModel bashImageModel);
        void stopLoading();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void loadDataFromServer(boolean clear);

        MainListAdapter getAdapter();

        void loadMore(int page);
    }
}
