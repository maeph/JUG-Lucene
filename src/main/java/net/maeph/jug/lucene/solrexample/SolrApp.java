package net.maeph.jug.lucene.solrexample;

import net.maeph.jug.lucene.LuceneExample;
import net.maeph.jug.lucene.config.AppConfig;
import net.maeph.jug.lucene.data.DatasetResource;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;


@Component
public class SolrApp {

    @Autowired
    private DatasetResource resource;
    @Autowired
    private SolrServer solrServer;

    public void index() throws IOException, SolrServerException {
        resource.forEach(element -> {
            try {

                solrServer.add(fromMap(element));
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        });
        solrServer.commit();
       
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

    public static void main(String[] args) throws IOException, SolrServerException, ParseException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SolrApp solrApp = context.getBean(SolrApp.class);

        solrApp.index();
        solrApp.query();
    }
    
}
