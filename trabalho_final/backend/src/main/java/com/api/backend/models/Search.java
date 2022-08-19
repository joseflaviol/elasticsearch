package com.api.backend.models;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.rest_client.RestClientTransport;

public class Search {
    private String host;
    private int port;

    public Search(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public RestClient restClient() {
        Header[] defaultHeaders = new Header[]{new BasicHeader("Content-Type", "application/json")};

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("admin", "admin"));

        return RestClient.builder(new HttpHost(host, port, "https"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
                .setDefaultHeaders(defaultHeaders)
                .build();
    }

    public OpenSearchClient osClient() {
        OpenSearchTransport transport = new RestClientTransport(
                restClient(), new JacksonJsonpMapper());

        return new OpenSearchClient(transport);
    }
}
