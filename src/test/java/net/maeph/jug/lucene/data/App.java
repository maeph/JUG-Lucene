package net.maeph.jug.lucene.data;

import net.maeph.jug.lucene.config.AppConfig;
import net.maeph.jug.lucene.luceneexample.LuceneApp;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Created by mephi_000 on 25.05.14.
 */
public class App {

    public static void main(String[] args) throws IOException, ParseException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        LuceneApp luceneApp = context.getBean(LuceneApp.class);
//        luceneApp.index();
        luceneApp.query();

    }
}
