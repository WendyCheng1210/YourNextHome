package org.yanwen.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Configuration
@Profile("unit")
public class AWSTestConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonS3 getAmazonS3(){
        return mock(AmazonS3.class);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public AmazonSQS getAmazonSQS(){
        //stub getQueueUrl
        AmazonSQS amazonSQS = mock(AmazonSQS.class);
        GetQueueUrlResult stubResult = new GetQueueUrlResult();
        when(amazonSQS.getQueueUrl(anyString())).thenReturn(stubResult);
        return amazonSQS;
    }
}
