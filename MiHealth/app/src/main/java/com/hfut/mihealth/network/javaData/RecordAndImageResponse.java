package com.hfut.mihealth.network.javaData;

import com.hfut.mihealth.network.data.Image;

import java.util.List;
import java.util.Map;

public class RecordAndImageResponse {
    Map<String, List<RecordResponse>> record;
    List<Image> image;

    public void setDietRecords(Map<String, List<RecordResponse>> dietRecords) {
        this.record = dietRecords;
    }
    public void setImageList(List<Image> imageList) {
        this.image = imageList;
    }
}
