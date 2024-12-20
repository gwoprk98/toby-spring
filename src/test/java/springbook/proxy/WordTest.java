package springbook.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Proxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.NameMatchMethodPointcut;
import springbook.proxy.domain.ProxyWord;
import springbook.proxy.domain.Word;
import springbook.proxy.domain.GundamWord;

class WordTest {

    @DisplayName(value = "ProxyWord를 이용한 word 테스트")
    @Test
    void proxyWord() {
        // given
        Word word = new ProxyWord(new GundamWord());

        // when & then
        assertThat(word.sayHello()).isEqualTo("HELLO GUNDAMDEV!");
        assertThat(word.sayThanks()).isEqualTo("THANK YOU GUNDAMDEV!");
    }

    @DisplayName(value = "InvocationHandler를 이용해 다이나믹 프록시 생성")
    @Test
    void dynamicProxy() {
        // given
        Word proxy = (Word) Proxy.newProxyInstance(
                getClass().getClassLoader(),                // 다이나믹 프록시가 정의되는 클래스 로더
                new Class[]{Word.class},                    // 다이나믹 프록시가 구현해야 하는 인터페이스 (여러개 가능)
                new UppercaseHandler(new GundamWord())     // InvocationHandler 구현 오브젝트
        );

        // when & then
        assertThat(proxy.sayHello()).isEqualTo("HELLO GUNDAMDEV!");
        assertThat(proxy.sayThanks()).isEqualTo("THANK YOU GUNDAMDEV!");
    }

    @DisplayName(value = "ProxyFactoryBean를 이용해 다이나믹 프록시 생성")
    @Test
    void proxyFactoryBean() {
        // given
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new GundamWord());
        proxyFactoryBean.addAdvice(new UppercaseAdvice());

        Word proxy = (Word) proxyFactoryBean.getObject();

        // when & then
        assertThat(proxy.sayHello()).isEqualTo("HELLO GUNDAMDEV!");
        assertThat(proxy.sayThanks()).isEqualTo("THANK YOU GUNDAMDEV!");
    }

    @DisplayName(value = "Advisor를 통해 부가 기능 및 적용 대상 설정")
    @Test
    void pointcut() {
        // given
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(new GundamWord());

        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedName("sayH*");

        proxyFactoryBean.addAdvisor(new UppercaseAdvisor(pointcut, new UppercaseAdvice()));

        Word proxy = (Word) proxyFactoryBean.getObject();

        // when & then
        assertThat(proxy.sayHello()).isEqualTo("HELLO GUNDAMDEV!");
        assertThat(proxy.sayThanks()).isEqualTo("Thank you gundamdev!");
    }
}
