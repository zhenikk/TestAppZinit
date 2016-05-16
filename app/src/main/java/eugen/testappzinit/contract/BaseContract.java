package eugen.testappzinit.contract;

import android.content.Context;

/**
 * Created by Yevhenii on 16.05.16.
 */
public interface BaseContract {

    interface View {

        Context getContext();

    }

    interface Presenter <V extends View> {

        void attachView(V view);

        void detachView();

    }

}