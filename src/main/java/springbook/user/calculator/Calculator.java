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
        return calculate((line, value) -> Integer.parseInt(line) + value);
    }

    public int max() {
        return calculate((line, value) -> Math.max(Integer.parseInt(line), value));
    }

    private int  calculate(LineCallback lineCallback) {
        try (FileReader fileReader = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fileReader)) {
            int result = 0;
            String line;

            while ((line = br.readLine()) != null) {
                result = lineCallback.executeWithLine(line, result);
            }
            return result;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
