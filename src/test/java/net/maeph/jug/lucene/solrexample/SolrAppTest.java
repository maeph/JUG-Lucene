package net.maeph.jug.lucene.solrexample;

import org.apache.solr.common.SolrInputDocument;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mephi_000 on 25.05.14.
 */
public class SolrAppTest {
    
    @Test
    public void shouldConvertMapToSolrInputDocument() throws Exception {
        Map element = new HashMap();
        element.put("id", 1.0d);
        ArrayList<String> tags = Lists.newArrayList("sit", "dolorem");
        element.put("tags", tags);
        element.put("text", "this is a text field");


        SolrInputDocument document = new SolrApp().fromMap(element);
        assertThat(document.getFieldValue("id")).isEqualTo(1);
        assertThat(document.getFieldValues("tags")).containsExactly("sit", "dolorem");
    }
    
}
