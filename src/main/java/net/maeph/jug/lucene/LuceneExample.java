package net.maeph.jug.lucene;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by mephi_000 on 25.05.14.
 */
public interface LuceneExample {
    
    
    void index() throws IOException, SolrServerException;
    void query() throws IOException, ParseException, SolrServerException;
    
}
