package net.maeph.jug.lucene.luceneexample;


import net.maeph.jug.lucene.LuceneExample;
import net.maeph.jug.lucene.config.AppConfig;
import net.maeph.jug.lucene.data.DatasetResource;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.assertj.core.util.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

@Component
public class LuceneApp {

    public static final Version MATCH_VERSION = Version.LUCENE_48;
    public static final String INDEX_PATH = "c:\\jug\\lucene\\lucene\\index";

    
    public static final Analyzer ANALYZER = new StandardAnalyzer(Version.LUCENE_48);
    @Autowired
    DatasetResource resource;

    public void index() {
        try (IndexWriter writer = getWriter()){
            resource.forEach(entry -> {
                try {
                    writer.addDocument(fromMap(entry));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Query getQuery() throws ParseException {
        QueryParser parser = new QueryParser(Version.LUCENE_48, "name", ANALYZER);
        return parser.parse("Luna");
    }

    public void query() throws IOException, ParseException {
        IndexSearcher searcher = getSearcher();

        TopDocs search = searcher.search(getQuery(), 10);
        
        Stream.of(search.scoreDocs)
                .map(scoreDoc -> extractDocument(searcher, scoreDoc))
                .map(document ->
                        String.format(
                                "name:%s\n" +
                                "gender:%s\n" +
                                "about:%s\n", 
                                document.get("name"), 
                                document.get("gender"), 
                                document.get("about"))
                ).forEach(System.out::println);
        }

    private IndexSearcher getSearcher() throws IOException {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(INDEX_PATH)));
        return new IndexSearcher(reader);
    }

    private Document extractDocument(IndexSearcher searcher, ScoreDoc scoreDoc) {
        try {
            return searcher.doc(scoreDoc.doc);
        } catch (IOException e) {
            return new Document();
        }
    }

    private IndexWriter getWriter() throws IOException {
        File path = new File(INDEX_PATH);
        
        if (!path.exists()) {
            path = Files.newFolder(INDEX_PATH);            
        }

        FSDirectory directory = FSDirectory.open(path);
        IndexWriterConfig iwc = new IndexWriterConfig(MATCH_VERSION, ANALYZER);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        return new IndexWriter(directory, iwc);
    }
    
    
    Document fromMap(Map element) {
        Document doc = new Document();
        element.forEach((key, value) -> {
            if (value instanceof Double)
                addDoubleValue(doc, key, value);
            else if (value instanceof String) {
                addStringValue(doc, key, value);
            } else if (value instanceof Iterable) {
                addMultiStringValue(doc, key, value);
            }
            
        });
        
        return doc;
    }

    private void addMultiStringValue(Document doc, Object key, Object value) {
        ((Iterable) value).forEach(
                subValue -> doc.add(new StringField(key.toString(), subValue.toString(), Field.Store.YES))
                
                
        );
    }

    private void addStringValue(Document doc, Object key, Object value) {
        doc.add(new TextField(key.toString(),value.toString(), Field.Store.YES));
    }

    private void addDoubleValue(Document doc, Object key, Object value) {
        doc.add(new LongField(key.toString(),((Double)value).intValue(), Field.Store.YES));
    }


    public static void main(String[] args) throws IOException, ParseException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        LuceneApp luceneApp = context.getBean(LuceneApp.class);
        
        luceneApp.index();
        luceneApp.query();
    }
}
