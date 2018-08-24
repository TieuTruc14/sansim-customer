package com.osp.security;

import com.osp.model.Customer;
import com.osp.web.service.customer.CustomerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Admin on 12/14/2017.
 */
public class SanSimUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerService customerService;

    public static final String ROLE_USER = "ROLE_USER";

    @Override
    public Customer loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = new Customer();
        customer = customerService.getUserInfo(username,"username");

        if (customer != null) {
            List<GrantedAuthority> lstAuths = new ArrayList<GrantedAuthority>();
            lstAuths.add(new SimpleGrantedAuthority(ROLE_USER));
            customer.setGrantedAuths(lstAuths);
        } else {
            throw new UsernameNotFoundException("No customer found for username '" + username + "'.");
        }

        return customer;
    }
}
