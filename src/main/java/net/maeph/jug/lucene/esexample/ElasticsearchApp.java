package net.maeph.jug.lucene.esexample;

import net.maeph.jug.lucene.LuceneExample;
import net.maeph.jug.lucene.data.DatasetResource;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.solr.client.solrj.SolrServerException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class ElasticsearchApp implements LuceneExample {

    @Autowired
    private DatasetResource resource;

    @Autowired
    private Client client;

    @Override
    public void index() throws IOException, SolrServerException {
        resource.forEach(element -> {
            client.prepareIndex("jug", "people", getStringedId(element))
                    .setSource(element)
                    .execute()
                    .actionGet();
        });

    }

    private String getStringedId(Map element) {
        return String.format("%d", ((Double) element.get("id")).intValue());
    }

    @Override
    public void query() throws IOException, ParseException, SolrServerException {
        SearchResponse searchResponse = client.prepareSearch("jug")
                .setTypes("people")
                .setQuery(QueryBuilders.matchQuery("name", "luna"))
                .execute()
                .actionGet();
        System.out.println(searchResponse.getHits().getTotalHits());
        
        searchResponse.getHits().forEach(document -> {
            System.out.println(String.format(
                    "name:%s\n" +
                    "gender:%s\n" +
                    "about:%s\n",
                    document.getSource().get("name"),
                    document.getSource().get("gender"),
                    document.getSource().get("about"))
            );

        });
    }
}
