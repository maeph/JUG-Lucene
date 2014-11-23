package net.maeph.jug.lucene.data;


import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;


import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by mephi_000 on 25.05.14.
 */
public class DatasetResourceTest {

    @Test
    public void shouldReadSingleJSONelement() throws Exception {


        StringReader reader = new StringReader(getJsonString());
        DatasetResource resourceUnderTest = DatasetResource.from(reader);

        Iterator<Map> iterator = resourceUnderTest.iterator();
        assertTrue(iterator.hasNext());
        Map element = iterator.next();
        assertFalse(iterator.hasNext());
        assertThat(element.get("id")).isEqualTo(0.0);


    }

    

    private String getJsonString() {
        return "  [{\n" +
                "        \"id\": 0,\n" +
                "        \"guid\": \"f6440597-f5a0-4334-8025-f8efdb29bf2a\",\n" +
                "        \"age\": 20,\n" +
                "        \"name\": \"Millie Cote\",\n" +
                "        \"gender\": \"female\",\n" +
                "        \"company\": \"ANDRYX\",\n" +
                "        \"email\": \"milliecote@andryx.com\",\n" +
                "        \"phone\": \"+1 (886) 436-2232\",\n" +
                "        \"address\": \"909 Kenilworth Place, Greenwich, South Carolina, 6724\",\n" +
                "        \"about\": \"Officia consectetur cillum exercitation nostrud. Elit cillum sunt aliquip id in ad commodo voluptate proident duis ipsum sint. Nulla officia ea ullamco consectetur commodo occaecat aute ex.\\r\\n\",\n" +
                "        \"tags\": [\n" +
                "            \"elit\",\n" +
                "            \"adipisicing\"\n" +
                "        ],\n" +
                "        \"favoriteFruit\": \"apple\"\n" +
                "    }]";
    }


}
