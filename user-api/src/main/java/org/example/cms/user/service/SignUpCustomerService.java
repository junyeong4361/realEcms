package org.example.cms.user.service;

import lombok.RequiredArgsConstructor;
import org.example.cms.user.domain.SignUpForm;
import org.example.cms.user.domain.model.Customer;
import org.example.cms.user.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;
    public Customer signUp(SignUpForm form) {
        return customerRepository.save(Customer.from(form));
    }
}
