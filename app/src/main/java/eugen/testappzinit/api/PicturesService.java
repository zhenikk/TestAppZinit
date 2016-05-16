package eugen.testappzinit.api;

import java.util.List;

import eugen.testappzinit.Constants;
import eugen.testappzinit.model.BashImageModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Yevhenii on 16.05.16.
 */


public class PicturesService {
    PicturesAPI picturesApi;

    public PicturesService() {
        picturesApi = ServiceFactory.createRetrofitService(PicturesAPI.class);
    }

    public PicturesAPI getApi() {
        return picturesApi;
    }

    public interface PicturesAPI {
    //https://api.ukrbash.org/1/pictures.getTheBest.json?client=ключ

        @GET("/1/pictures.getTheBest.json?client="+ Constants.API_KEY)
        Call<List<BashImageModel>> getBestPictures(@Query("start") int start,@Query("limit") int limit);

    }
}
