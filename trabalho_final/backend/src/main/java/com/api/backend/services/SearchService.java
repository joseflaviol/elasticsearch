package com.api.backend.services;

import com.api.backend.models.Document;
import com.api.backend.models.Result;
import com.api.backend.models.Search;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    public Result search(String must, String must_field, boolean must_match_phrase, String must_not, String must_not_field, boolean must_not_match_phrase, String should, String should_field, boolean should_match_phrase, int page, int page_size) throws IOException {
        Search search = new Search("localhost", 9200);

        OpenSearchClient client = search.osClient();

        SearchResponse<Document> results = client.search(s -> s
                        .index("wikipedia")
                        .query(q -> q
                                .bool(b -> b
                                        .must(query -> {
                                                if (must_match_phrase) {
                                                    return query.matchPhrase(mf -> mf
                                                            .query(must)
                                                            .field(must_field)
                                                    );
                                                }
                                                return query.match(m -> m
                                                        .query(matchQuery -> matchQuery
                                                                .stringValue(must)
                                                        )
                                                        .field(must_field)
                                                );
                                            }
                                        )
                                        .mustNot(query -> {
                                                    if (must_not_match_phrase) {
                                                        return query.matchPhrase(mf -> mf
                                                                .query(must_not)
                                                                .field(must_not_field)
                                                        );
                                                    }
                                                    return query.match(m -> m
                                                            .query(matchQuery -> matchQuery
                                                                    .stringValue(must_not)
                                                            )
                                                            .field(must_not_field)
                                                    );
                                                }
                                        )
                                        .should(query -> {
                                                    if (should_match_phrase) {
                                                        return query.matchPhrase(mf -> mf
                                                                .query(should)
                                                                .field(should_field)
                                                        );
                                                    }
                                                    return query.match(m -> m
                                                            .query(matchQuery -> matchQuery
                                                                    .stringValue(should)
                                                            )
                                                            .field(should_field)
                                                    );
                                                }
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
