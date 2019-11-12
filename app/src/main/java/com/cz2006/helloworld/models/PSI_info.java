package com.cz2006.helloworld.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Represents PSI Info
 *
 * @author Edmund
 *
 */
public class PSI_info {

    @SerializedName("items")
    @Expose
    private List<PSI_item> items = null;

    public List<PSI_item> getItems() {
        return items;
    }

    public void setItems(List<PSI_item> items) {
        this.items = items;
    }
}
