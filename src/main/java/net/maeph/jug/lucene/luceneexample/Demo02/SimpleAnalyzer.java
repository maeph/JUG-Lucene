package net.maeph.jug.lucene.luceneexample.Demo02;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by mephi_000 on 2014-11-22.
 */
public class SimpleAnalyzer extends Analyzer {




    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {

        WhitespaceTokenizer tokenizer = new WhitespaceTokenizer(reader);
        
        return new TokenStreamComponents(tokenizer);
        
    }


    public static void main(String[] args) throws IOException {
       
        Analyzer analyzer = new SimpleAnalyzer();
        TokenStream ts = analyzer.tokenStream("field1", "to jest przyklad");
        ts.reset();
        while (ts.incrementToken()) {
            System.out.println("token:" + ts.toString());
        }
        ts.end();
        

    }
}
