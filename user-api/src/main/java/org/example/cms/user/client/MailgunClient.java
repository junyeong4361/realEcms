package org.example.cms.user.client;

import feign.Response;
import org.example.cms.user.client.mailgun.SendMailForm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "mailgun", url = "https://api.mailgun.net/v3/")
@Qualifier("mailgun")
public interface MailgunClient {
    @PostMapping("sandbox8121e09284714e4d93b9ded77a85603d.mailgun.org/messages")
    Response sendEmail(@SpringQueryMap SendMailForm form);

}
