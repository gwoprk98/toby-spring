package springbook.user.calculator;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.net.URL;
import java.util.Objects;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        URL url = getClass().getResource("/numbers.txt");
        String path = Objects.requireNonNull(url).getPath();
        Calculator calculator = new Calculator(path);
    }

    @DisplayName(value = "file 숫자들의 합")
    @Test
    void sum() {


        //when
        int sum = calculator.sum();

        //then
        assertThat(sum).isEqualTo(10);
    }

    @DisplayName(value = "file 숫자 중에서 최대값")
    @Test
    void max() {


        //when
        int max = calculator.max();

        //then
        assertThat(max).isEqualTo(4);
    }
}

