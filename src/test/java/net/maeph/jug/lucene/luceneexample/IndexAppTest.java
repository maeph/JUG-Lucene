package net.maeph.jug.lucene.luceneexample;

import org.apache.lucene.document.Document;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mephi_000 on 25.05.14.
 */
public class IndexAppTest {
    
    @Test
    public void shouldConvertMapToLuceneDocument() throws Exception {
        Map element = new HashMap();
        element.put("id", 1.0d);
        ArrayList<String> tags = Lists.newArrayList("sit", "dolorem");
        element.put("tags", tags);
        element.put("text", "this is a text field");


        Document document = new LuceneApp().fromMap(element);
        assertThat(document.get("id")).isEqualTo("1");
        assertThat(document.getValues("tags")).containsExactly("sit", "dolorem");
        
    }
    
}
