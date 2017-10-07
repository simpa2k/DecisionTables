package parser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvParser implements Parser {

    public List<String[]> parse(Stream<String> fileStream) {

        if (fileStream == null) {
            throw new IllegalArgumentException("fileStream may not be null.");
        }

        /*
         * Split each line on commas and put the resulting String arrays in a list.
         */
        return fileStream.map(line -> line.split(",")).collect(Collectors.toList());

    }
}
