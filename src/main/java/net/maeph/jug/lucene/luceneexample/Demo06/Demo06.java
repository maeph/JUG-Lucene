package net.maeph.jug.lucene.luceneexample.Demo06;

import net.maeph.jug.lucene.luceneexample.LuceneJUGUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/**
 * Created by mephi_000 on 2014-11-23.
 */
public class Demo06 {

    public static void main(String[] args) throws IOException, ParseException {

        Analyzer analyzer = new StandardLowercaseWithStempelAndSynonymsAnalyzer();
        LuceneJUGUtils luceneJUGUtils = new LuceneJUGUtils();

        
        
        System.out.println("Indexing...");
        luceneJUGUtils.index("dataset1.json", analyzer);
        System.out.println("Querying (dużymi)...");
        luceneJUGUtils.query("opis", "dużymi", analyzer);
        System.out.println("Querying (wielkimi)...");
        luceneJUGUtils.query("opis", "wielkimi", analyzer);
        System.out.println("Querying (litery)...");
        luceneJUGUtils.query("opis", "litery", analyzer);
    }
}
