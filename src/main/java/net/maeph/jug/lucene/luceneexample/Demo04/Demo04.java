package net.maeph.jug.lucene.luceneexample.Demo04;

import net.maeph.jug.lucene.luceneexample.IndexOperations;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/**
 * Created by mephi_000 on 2014-11-23.
 */
public class Demo04 {

    public static void main(String[] args) throws IOException, ParseException {

        IndexOperations indexOperations = new IndexOperations();
        
        
        Analyzer analyzer = new StandardLowercaseAnalyzer();
        
        System.out.println("Indexing...");
        indexOperations.index("dataset1.json", analyzer);
        System.out.println("Querying (dużymi)...");
        indexOperations.query("opis", "dużymi", analyzer);
        System.out.println("Querying (duzymi)...");
        indexOperations.query("opis", "duzymi", analyzer);
    }
}
