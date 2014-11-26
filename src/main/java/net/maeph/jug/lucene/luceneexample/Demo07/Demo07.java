package net.maeph.jug.lucene.luceneexample.Demo07;

import net.maeph.jug.lucene.luceneexample.IndexOperations;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/**
 * Created by mephi_000 on 2014-11-23.
 */
public class Demo07 {

    public static void main(String[] args) throws IOException, ParseException {

        Analyzer analyzer = new HTMLStandardLowercaseWithStempelAndSynonymsAnalyzer();
        IndexOperations indexOperations = new IndexOperations();

        
        
        System.out.println("Indexing...");
        indexOperations.index("dataset1.json", analyzer);
        System.out.println("Querying (dużymi)...");
        indexOperations.query("opis", "dużymi", analyzer);
        System.out.println("Querying (wielkimi)...");
        indexOperations.query("opis", "wielkimi", analyzer);
        System.out.println("Querying (litery)...");
        indexOperations.query("opis", "litery", analyzer);
    }
}
