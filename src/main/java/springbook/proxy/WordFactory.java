package springbook.proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class WordFactory implements FactoryBean<Word> {

    @Override
    public Word getObject() throws Exception {
        return new ProxyWord(new WooWord());
    }

    @Override
    public Class<?> getObjectType() {
        return WooWord.class;
    }

    @Override
    public boolean isSingleton() {
        return false;               // 이 팩토리 빈은 요청시마다 새로 생성하니 false로 지정
    }
}
