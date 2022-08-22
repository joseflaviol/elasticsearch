package com.api.backend.models;

import java.util.List;

public class Result {
    
    private List<Document> documents;
    private long total;

    public Result(List<Document> documents, long total) {
        this.documents = documents;
        this.total = total;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
    
    
    
}
