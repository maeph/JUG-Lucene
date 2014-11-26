package net.maeph.jug.lucene.luceneexample.Demo04;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by mephi_000 on 2014-11-22.
 */
public class StandardLowercaseAnalyzer extends Analyzer {




    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {

        Tokenizer tokenizer = new StandardTokenizer(reader);

        LowerCaseFilter tokenFilter = new LowerCaseFilter(tokenizer);
        
        return new TokenStreamComponents(tokenizer, tokenFilter);
        
    }


    public static void main(String[] args) throws IOException {
       
        Analyzer analyzer = new StandardLowercaseAnalyzer();
        TokenStream ts = analyzer.tokenStream("field1", "Dużymi,małymi");
        ts.reset();
        while (ts.incrementToken()) {
            System.out.println("token:" + ts.toString());
        }
        ts.end();
        

    }
}
