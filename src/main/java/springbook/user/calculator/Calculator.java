package springbook.user.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    private String filepath;

    public Calculator(final String filePath) {
        this.filepath = filePath;
    }

    public int sum() {
        return calculate(br -> br.lines()
                .mapToInt(Integer::parseInt)
                .sum());
    }

    public int max() {
        return calculate(br -> br.lines()
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0));
    }

    private int calculate(BufferedReaderCallback callback) {
        try (FileReader fileReader = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fileReader)) {
            return callback.calculateWithReader(br);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
