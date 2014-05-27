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
    
    @Autowired
    @Qualifier("jsonSource")
    private Reader inputReader;

    

    @Override
    public Iterator<Map> iterator() {
        Gson gson = new Gson();
        List list = gson.fromJson(inputReader, List.class);
        return list.iterator();
        
    }

}
