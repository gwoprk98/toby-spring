package springbook.proxy;

public class WooWord implements Word {

    private static final String NAME = "gwooprk";

    @Override
    public String sayHello() {
        return String.format("Hello %s!", NAME);
    }

    @Override
    public String sayThanks() {
        return String.format("Thank you %s!", NAME);
    }
}
