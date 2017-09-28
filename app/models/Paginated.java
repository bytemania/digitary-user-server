package models;

import java.util.List;

public class Paginated<T> {

    int pageSize;
    int pageNumber;
    int pageCount;
    List<T> data;

    public Paginated() {
    }

    public Paginated(int pageSize, int pageNumber, int totalRecords, List<T> data) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;

        this.pageCount = Double.valueOf(Math.ceil(((double)totalRecords / pageSize))).intValue();
        this.data = data;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
