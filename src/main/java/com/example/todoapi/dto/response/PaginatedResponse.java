package com.example.todoapi.dto.response;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> data;
    private int page;
    private int limit;
    private long total;

    // Constructors, getters, and setters
    public PaginatedResponse() {}

    public PaginatedResponse(List<T> data, int page, int limit, long total) {
        this.data = data;
        this.page = page;
        this.limit = limit;
        this.total = total;
    }

    // Getters and setters
    public List<T> getData() { return data; }
    public void setData(List<T> data) { this.data = data; }

    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getLimit() { return limit; }
    public void setLimit(int limit) { this.limit = limit; }

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
}