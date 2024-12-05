package springbook.proxy;

public class LogwooWord implements Word {

    private static final String NAME = "wooword";

    @Override
    public String sayHello() {
        return String.format("Hello %s!", NAME);
    }

    @Override
    public String sayThanks() {
        return String.format("Thank you %s!", NAME);
    }
}
