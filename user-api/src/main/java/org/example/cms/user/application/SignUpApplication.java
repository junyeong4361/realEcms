package org.example.cms.user.application;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.cms.user.client.MailgunClient;
import org.example.cms.user.client.mailgun.SendMailForm;
import org.example.cms.user.domain.SignUpForm;
import org.example.cms.user.domain.model.Customer;
import org.example.cms.user.exception.CustomException;
import org.example.cms.user.exception.ErrorCode;
import org.example.cms.user.service.SignUpCustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer c = signUpCustomerService.signUp(form);
            LocalDateTime now = LocalDateTime.now();

            String code = getRandomCode();

            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("jjunyeong4361@gmail.com")
                    .to(form.getEmail())
                    .subject("verification Email!")
                    .text(getVerificationMailBody(form.getEmail(), form.getName(), getRandomCode()))
                    .build();

            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.changeCustomerEmailValidateEmail(c.getId(), code);
            return "회원가입에 성공하였습니다.";
        }

    }

    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }
    private String getVerificationMailBody(String email, String name, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello").append(name).append('! Please Click Link for verification.\n\n')
                .append("http://localhost:8080/customer/signup/verifiy?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }
}
