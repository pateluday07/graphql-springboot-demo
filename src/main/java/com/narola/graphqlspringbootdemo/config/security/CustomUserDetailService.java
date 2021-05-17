package com.narola.graphqlspringbootdemo.config.security;

import com.narola.graphqlspringbootdemo.entity.Owner;
import com.narola.graphqlspringbootdemo.exception.CustomException;
import com.narola.graphqlspringbootdemo.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.narola.graphqlspringbootdemo.constant.Constants.USER_DOES_NOT_EXISTS_ERROR_MSG;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Owner owner = ownerRepository
                .getByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_DOES_NOT_EXISTS_ERROR_MSG));
        return new CustomUserDetail(owner);
    }
}
