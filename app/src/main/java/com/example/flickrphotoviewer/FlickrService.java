package com.example.flickrphotoviewer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cepreu on 29.08.17.
 */

public interface FlickrService {
    // FIXME: copy & paste api_key better in those conditions:
    // https://stackoverflow.com/questions/28264016/retrofit-and-constant-parameters-in-requests
    @GET("services/rest/?method=flickr.photos.search" +
            "&api_key=006b7cb814a0c9ae4c17666b880e5d6f" +
            "&format=json&nojsoncallback=1")
    Call<PhotosData> searchImages(@Query("text") String searchText);
    @GET("services/rest/?method=flickr.photos.getRecent" +
            "&api_key=006b7cb814a0c9ae4c17666b880e5d6f" +
            "&format=json&nojsoncallback=1")
    Call<PhotosData> latestImages();
    @GET("services/rest/?method=flickr.photos.getInfo" +
            "&api_key=006b7cb814a0c9ae4c17666b880e5d6f" +
            "&format=json&nojsoncallback=1")
    Call<OriginalPhotoData> getOriginalInfo(@Query("photo_id") String id,
                                            @Query("secret") String secret);

}
