package net.maeph.jug.lucene.data;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.*;
import java.util.function.Supplier;


@Component
public class DatasetResource implements Iterable<Map> {
    
  
    private Reader inputReader;

    private DatasetResource(Reader inputReader) {
        this.inputReader = inputReader;
    }

    public static DatasetResource from(Reader reader) {
        return new DatasetResource(reader);
    }
        
        
    @Override
    public Iterator<Map> iterator() {
        Gson gson = new Gson();
        List list = gson.fromJson(inputReader, List.class);
        return list.iterator();
        
    }

}
