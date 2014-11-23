package net.maeph.jug.lucene.luceneexample.Demo03;

import net.maeph.jug.lucene.luceneexample.Demo02.SimpleAnalyzer;
import net.maeph.jug.lucene.luceneexample.LuceneJUGUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/**
 * Created by mephi_000 on 2014-11-23.
 */
public class Demo03 {

    public static void main(String[] args) throws IOException, ParseException {

        LuceneJUGUtils luceneJUGUtils = new LuceneJUGUtils();
        
        
        Analyzer analyzer = new SimpleLowercaseAnalyzer();
        
        System.out.println("Indexing...");
        luceneJUGUtils.index("dataset1.json", analyzer);
        System.out.println("Querying...");
        luceneJUGUtils.query("opis", "dużymi", analyzer);
    }
}
