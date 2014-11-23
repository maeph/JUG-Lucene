package net.maeph.jug.lucene.luceneexample;

import org.apache.lucene.analysis.charfilter.BaseCharFilter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
* Created by mephi_000 on 2014-11-23.
*/
class XMarksTheSpaceCharFilter extends BaseCharFilter {

    
    Reader transformedInput;
    /**
     * Create a new CharFilter wrapping the provided reader.
     *
     * @param input a Reader, can also be a CharFilter for chaining.
     */
    public XMarksTheSpaceCharFilter(Reader input) {
        super(input);
    }
    @Override
    protected int correct(int currentOff) {
        init();
        return super.correct(currentOff);
    }

    private void init() {
        if (transformedInput == null) {
            try {
                transformedInput = tranform();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Reader tranform() throws IOException {
        
        String value = readReader(input);
        return  new StringReader(value.replace("x", " "));   
    }

    @Override
    public int read() throws IOException {
        return transformedInput.read();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        init();
        return transformedInput.read(cbuf, off, len);
    }

    public static String readReader(Reader input) throws IOException {
        StringBuilder buffered = new StringBuilder();
        char [] temp = new char [1024];
        for (int cnt = input.read(temp); cnt > 0; cnt = input.read(temp)) {
            buffered.append(temp, 0, cnt);
        }
        
        return buffered.toString();
    }
}
