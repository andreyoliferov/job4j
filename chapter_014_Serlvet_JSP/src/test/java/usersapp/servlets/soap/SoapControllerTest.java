package usersapp.servlets.soap;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

/**
 * @autor aoliferov
 * @since 09.09.2019
 */
public class SoapControllerTest {

    /**
     * Пример клиента для веб-сервиса. Ошибка после миграции на java 11.
     */
    @Test
    @Ignore
    public void soapClient() {

        String soapServiceUrl = "http://localhost:8080/user_app/services/endpoint";
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(SoapController.class);
        factoryBean.setAddress(soapServiceUrl);
        SoapController webservice = (SoapController) factoryBean.create();

        String result = webservice.testService();
        System.out.println(result);
    }

}
