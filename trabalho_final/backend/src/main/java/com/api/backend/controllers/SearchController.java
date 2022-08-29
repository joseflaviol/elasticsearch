package com.api.backend.controllers;

import com.api.backend.models.Document;
import com.api.backend.models.Result;
import com.api.backend.services.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.swing.text.html.Option;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("")
    public Result search(@RequestParam(name = "must") String must, @RequestParam(name = "must_field", defaultValue = "content") String must_field, @RequestParam(name = "must_match_phrase") Optional<Boolean> must_match_phrase, @RequestParam(name = "must_not", defaultValue = "") String must_not, @RequestParam(name = "must_not_field", defaultValue = "content") String must_not_field, @RequestParam(name = "must_not_match_phrase") Optional<Boolean> must_not_match_phrase, @RequestParam(name = "should", defaultValue = "") String should, @RequestParam(name = "should_field", defaultValue = "content") String should_field, @RequestParam(name = "should_match_phrase") Optional<Boolean> should_match_phrase, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(name = "page_size", defaultValue = "10") int page_size) throws IOException {
        return this.searchService.search(must, must_field, must_match_phrase.orElse(false), must_not, must_not_field, must_not_match_phrase.orElse(false), should, should_field, should_match_phrase.orElse(false), page, page_size);
    }

}
