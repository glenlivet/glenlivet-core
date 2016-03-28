package org.glenlivet.core;

import java.util.List;

/**
 * Created by appledev122 on 3/28/16.
 */
public class PagingResult<T> {

    private List<T> data;

    private int total;

    public PagingResult(List<T> data, int total) {
        this.data = data;
        this.total = total;
    }

    public static <T> PagingResult<T> newPagingResult(int total, List<T> data) {
        PagingResult<T> retVal = new PagingResult<>(data, total);
        return retVal;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
