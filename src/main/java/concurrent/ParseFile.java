package concurrent;

import java.io.*;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent() throws IOException {
        return content(ch -> true);
    }

    public String getContentWithoutUnicode() throws IOException {
        return content(ch -> ch < 0x80);
    }

    private String content(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = reader.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }
}
