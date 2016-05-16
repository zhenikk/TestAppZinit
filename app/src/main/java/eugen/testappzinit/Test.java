package eugen.testappzinit;

import android.util.Log;

import java.util.List;

import eugen.testappzinit.api.PicturesService;
import eugen.testappzinit.model.BashImageModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Yevhenii on 16.05.16.
 */
public class Test {
    private static final String TAG = "Test";

    public Test() {
        testPictures();
    }

    private void testPictures() {
        PicturesService service = new PicturesService();

        Call<List<BashImageModel>> call = service.getApi().getBestPictures(0,25);
        call.enqueue(new Callback<List<BashImageModel>>() {

            @Override
            public void onResponse(Call<List<BashImageModel>> call, Response<List<BashImageModel>> response) {

               // Log.d(TAG, "Image name=" + response.body().get(0).getTitle());

            }

            @Override
            public void onFailure(Call<List<BashImageModel>> call, Throwable t) {

            }
        });


    }
}
