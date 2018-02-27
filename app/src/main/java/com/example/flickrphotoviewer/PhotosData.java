package com.example.flickrphotoviewer;

import java.util.List;

/**
 * Created by cepreu on 29.08.17.
 */

public class PhotosData {
    public static class Page{
        private List<Photo> photo;

        public List<Photo> getPhoto() {
            return photo;
        }

        public void setPhoto(List<Photo> photo) {
            this.photo = photo;
        }
    }
    private Page photos;

    public Page getPhotos() {
        return photos;
    }

    public void setPhotos(Page photos) {
        this.photos = photos;
    }
}
