package net.maeph.jug.lucene.luceneexample;


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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.assertj.core.util.Files;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;


public class LuceneJUGUtils {

    public static final Version MATCH_VERSION = Version.LUCENE_4_10_2;
    public static final String INDEX_PATH = "c:\\jug\\lucene\\lucene\\index";


    private static ResourceLoader resourceLoader = new FileSystemResourceLoader();


    public static Reader jsonSource(String filename) throws IOException {
        Resource resource = resourceLoader.getResource(filename);
        return java.nio.file.Files.newBufferedReader(Paths.get(resource.getURI()));
    }

    public void index(String fileName, Analyzer analyzer) {
        try (IndexWriter writer = getWriter()) {
            DatasetResource.from(jsonSource(fileName))
                    .forEach(entry -> {
                        try {
                            writer.addDocument(fromMap(entry), analyzer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            writer.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Query getQuery(String field, String value, Analyzer analyzer) throws ParseException {
        QueryParser parser = new QueryParser(field, analyzer);
        return parser.parse(value);
    }

    public void query(String field, String value, Analyzer analyzer) throws IOException, ParseException {
        IndexSearcher searcher = getSearcher();

        TopDocs search = searcher.search(getQuery(field, value, analyzer), 10);
        if (search.scoreDocs.length > 0) {
            printResults(searcher, search, field);
        } else {
            System.out.println("No results");
        }
    }

    private void printResults(IndexSearcher searcher, TopDocs search, String field) {
        Stream.of(search.scoreDocs)
                .map(scoreDoc -> extractDocument(searcher, scoreDoc))
                .map(document -> String.format("%s:%s\n", field, document.get(field))
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
        File path = getIndexDir();

        Directory directory = FSDirectory.open(path);
        IndexWriterConfig iwc = new IndexWriterConfig(MATCH_VERSION, new StandardAnalyzer());
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        return new IndexWriter(directory, iwc);
    }

    private File getIndexDir() {
        File path = new File(INDEX_PATH);

        if (!path.exists()) {
            path = Files.newFolder(INDEX_PATH);
        }
        return path;
    }


    Document fromMap(Map element) {
        System.out.println(element.toString());
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
        doc.add(new TextField(key.toString(), value.toString(), Field.Store.YES));
    }

    private void addDoubleValue(Document doc, Object key, Object value) {
        doc.add(new LongField(key.toString(), ((Double) value).intValue(), Field.Store.YES));
    }


}
