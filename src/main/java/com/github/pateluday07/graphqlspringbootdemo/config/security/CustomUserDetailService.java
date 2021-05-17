package com.github.pateluday07.graphqlspringbootdemo.config.security;

import com.github.pateluday07.graphqlspringbootdemo.constant.Constants;
import com.github.pateluday07.graphqlspringbootdemo.entity.Owner;
import com.github.pateluday07.graphqlspringbootdemo.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final OwnerRepository ownerRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Owner owner = ownerRepository
                .getByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(Constants.USER_DOES_NOT_EXISTS_ERROR_MSG));
        return new CustomUserDetail(owner);
    }
}
