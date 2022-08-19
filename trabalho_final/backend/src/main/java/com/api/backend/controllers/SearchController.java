package com.api.backend.controllers;

import com.api.backend.models.Document;
import com.api.backend.services.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("")
    public List<Document> search(@RequestParam(name = "must") String must, @RequestParam(name = "must_not", defaultValue = "") String must_not, @RequestParam(name = "should", defaultValue = "") String should, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "page_size", defaultValue = "10") int page_size) throws IOException {
        return this.searchService.search(must, must_not, should, page, page_size);
    }

}
