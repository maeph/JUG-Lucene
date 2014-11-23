package net.maeph.jug.lucene.luceneexample.Demo01;

import net.maeph.jug.lucene.luceneexample.LuceneJUGUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/**
 * Created by mephi_000 on 2014-11-23.
 */
public class Demo01 {

    public static void main(String[] args) throws IOException, ParseException {

        LuceneJUGUtils luceneJUGUtils = new LuceneJUGUtils();
        
        
        StandardAnalyzer analyzer = new StandardAnalyzer();
        
        System.out.println("Indexing...");
        luceneJUGUtils.index("dataset.json", analyzer);
        System.out.println("Querying...");
        luceneJUGUtils.query("name", "Maddox", analyzer);
    }
}
