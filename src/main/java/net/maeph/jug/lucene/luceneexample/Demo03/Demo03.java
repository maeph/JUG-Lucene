package net.maeph.jug.lucene.luceneexample.Demo03;

import net.maeph.jug.lucene.luceneexample.IndexOperations;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/**
 * Created by mephi_000 on 2014-11-23.
 */
public class Demo03 {

    public static void main(String[] args) throws IOException, ParseException {

        IndexOperations indexOperations = new IndexOperations();
        
        
        Analyzer analyzer = new SimpleLowercaseAnalyzer();
        
        System.out.println("Indexing...");
        indexOperations.index("dataset1.json", analyzer);
        System.out.println("Querying...");
        indexOperations.query("opis", "dużymi", analyzer);
    }
}
