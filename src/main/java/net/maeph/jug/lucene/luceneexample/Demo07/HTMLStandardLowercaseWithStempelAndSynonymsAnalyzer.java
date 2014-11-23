package net.maeph.jug.lucene.luceneexample.Demo07;

import net.maeph.jug.lucene.luceneexample.Demo06.StandardLowercaseWithStempelAndSynonymsAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.pl.PolishAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.stempel.StempelFilter;
import org.apache.lucene.analysis.stempel.StempelStemmer;
import org.apache.lucene.analysis.synonym.SynonymFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by mephi_000 on 2014-11-22.
 */
public class HTMLStandardLowercaseWithStempelAndSynonymsAnalyzer extends Analyzer {

    

    @Override
    protected TokenStreamComponents createComponents(String fieldName, Reader reader) {

        Tokenizer tokenizer = new StandardTokenizer(new HTMLStripCharFilter(reader));

        StempelStemmer stemmer = new StempelStemmer(PolishAnalyzer.getDefaultTable());
        
        TokenFilter tokenFilter = 
                new LowerCaseFilter(
                    new SynonymFilter(        
                        new StempelFilter(tokenizer, stemmer),
                        getSynonyms(),
                        true
                    )
                );
        
        return new TokenStreamComponents(tokenizer, tokenFilter);
        
    }

    
    private SynonymMap getSynonyms() {
        SynonymMap.Builder builder = new SynonymMap
                .Builder(false);
        builder.add(new CharsRef("duży"), new CharsRef("wielki"), true);
        try {
            return builder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) throws IOException {
       
        Analyzer analyzer = new HTMLStandardLowercaseWithStempelAndSynonymsAnalyzer();
        TokenStream ts = analyzer.tokenStream("field1", "<H1>Tekst z polskimi i <div class=\"duży\">niedużymi</div>, małymi literami</H1>");
        ts.reset();
        while (ts.incrementToken()) {
            System.out.println("token:" + ts.toString());
        }
        ts.end();
        

    }
}
