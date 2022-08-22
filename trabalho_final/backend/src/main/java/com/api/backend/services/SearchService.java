package com.api.backend.services;

import com.api.backend.models.Document;
import com.api.backend.models.Result;
import com.api.backend.models.Search;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    public Result search(String must, String must_not, String should, int page, int page_size) throws IOException {
        Search search = new Search("localhost", 9200);

        OpenSearchClient client = search.osClient();

        SearchResponse<Document> results = client.search(s -> s
                        .index("wikipedia")
                        .query(q -> q
                                .bool(b -> b
                                        .must(query -> query
                                                .match(m -> m
                                                        .query(matchQuery -> matchQuery
                                                                .stringValue(must)
                                                        )
                                                        .field("content")
                                                )
                                        )
                                        .mustNot(query -> query
                                                .match(m -> m
                                                        .query(matchQuery -> matchQuery
                                                                .stringValue(must_not)
                                                        )
                                                        .field("content")
                                                )
                                        )
                                        .should(query -> query
                                                .match(m -> m
                                                        .query(matchQuery -> matchQuery
                                                                .stringValue(should)
                                                        )
                                                        .field("content")
                                                )
                                        )
                                )
                        )
                        .from((page - 1) * page_size)
                        .size(page_size)
                , Document.class
        );

        List<Document> documents = results.hits().hits().stream().map(hit -> hit.source()).collect(Collectors.toList());
        long total = results.hits().total().value();
        
        return new Result(documents, total);
    }

}
