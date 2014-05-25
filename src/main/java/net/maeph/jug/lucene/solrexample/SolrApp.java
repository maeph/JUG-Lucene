package net.maeph.jug.lucene.solrexample;

import net.maeph.jug.lucene.LuceneExample;
import net.maeph.jug.lucene.data.DatasetResource;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


@Component
public class SolrApp implements LuceneExample {

    @Autowired
    private DatasetResource resource;

    @Autowired
    private SolrServer solrServer;

    @Override
    public void index() throws IOException, SolrServerException {
        resource.forEach(element -> {
            try {
                solrServer.add(fromMap(element), 1000);
                System.out.println("indexed:" + element.get("id"));
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
       
    }

    SolrInputDocument fromMap(Map element) {
        SolrInputDocument document = new SolrInputDocument();
        element.forEach((key, value) -> {
            if (value instanceof Double) {
                document.addField(key.toString(), ((Double)value).intValue());

            } else {
                document.addField(key.toString(), value);
            }
        });
        return document;
    }

    @Override
    public void query() throws IOException, ParseException, SolrServerException {

        QueryResponse query = solrServer.query(new SolrQuery().setQuery("name:luna"));
        query.getResults().forEach(document ->
                System.out.println(String.format(
                        "name:%s\n" +
                                "gender:%s\n" +
                                "about:%s\n",
                        document.get("name"),
                        document.get("gender"),
                        document.get("about"))));
    }
    
}
