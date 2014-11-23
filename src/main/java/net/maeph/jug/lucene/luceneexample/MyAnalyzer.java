package net.maeph.jug.lucene.luceneexample;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by mephi_000 on 2014-11-22.
 */
public class MyAnalyzer extends Analyzer {


    private Version matchVersion;

    public MyAnalyzer(Version matchVersion) {

        this.matchVersion = matchVersion;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {

        WhitespaceTokenizer tokenizer = new WhitespaceTokenizer(new XMarksTheSpaceCharFilter(reader));
        MyTokenFilter tokenFilter = new MyTokenFilter(tokenizer);
        return new TokenStreamComponents(tokenizer, tokenFilter);
        
    }


    public static void main(String[] args) throws IOException {
        Version matchVersion = Version.LUCENE_4_10_2;
        Analyzer analyzer = new MyAnalyzer(matchVersion);
        TokenStream ts = analyzer.tokenStream("field1", "Toxjestxzdaniexktorexniexmaxspacji");
        ts.reset();
        while (ts.incrementToken()) {
            System.out.println("token:" + ts.toString());
        }
        ts.end();
        

    }
}
