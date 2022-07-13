package demo.demo.config;

import org.mdnote.quickauth.core.ApiAuthenticator;
import org.mdnote.quickauth.core.HttpApiAuthenticator;
import org.mdnote.quickauth.hash.SHA256HashSignature;
import org.mdnote.quickauth.storge.PropertiesFileCredentialStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rhythm-2019
 * @date 2022/7/12
 * @description 配置类
 */

@Configuration
public class QuickAuthConfig {

    @Value("${quick-auth.path}")
    private String propertyFilePath;

    @Bean
    public HttpApiAuthenticator apiAuthenticator() {
        return new HttpApiAuthenticator(new PropertiesFileCredentialStorage(this.propertyFilePath), new SHA256HashSignature());
    }
}
