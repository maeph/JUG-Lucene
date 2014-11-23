package net.maeph.jug.lucene.luceneexample.Demo05;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.pl.PolishAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.stempel.StempelFilter;
import org.apache.lucene.analysis.stempel.StempelStemmer;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by mephi_000 on 2014-11-22.
 */
public class StandardLowercaseWithStempelAnalyzer extends Analyzer {




    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {

        Tokenizer tokenizer = new StandardTokenizer(reader);

        StempelStemmer stemmer = new StempelStemmer(PolishAnalyzer.getDefaultTable());
        
        TokenFilter tokenFilter = new LowerCaseFilter(new StempelFilter(tokenizer, stemmer));
        
        return new TokenStreamComponents(tokenizer, tokenFilter);
        
    }


    public static void main(String[] args) throws IOException {
       
        Analyzer analyzer = new StandardLowercaseWithStempelAnalyzer();
        TokenStream ts = analyzer.tokenStream("field1", "Dużymi");
        ts.reset();
        while (ts.incrementToken()) {
            System.out.println("token:" + ts.toString());
        }
        ts.end();
        

    }
}
