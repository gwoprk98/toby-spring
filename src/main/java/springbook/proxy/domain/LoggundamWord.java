package springbook.proxy.domain;

public class LoggundamWord implements Word {

    private static final String NAME = "gundamdev";

    @Override
    public String sayHello() {
        return String.format("Hello %s!", NAME);
    }

    @Override
    public String sayThanks() {
        return String.format("Thank you %s!", NAME);
    }
}
