package com.github.pateluday07.graphqlspringbootdemo.service;

import com.github.pateluday07.graphqlspringbootdemo.dto.RegistrationDto;
import com.github.pateluday07.graphqlspringbootdemo.entity.Owner;
import com.github.pateluday07.graphqlspringbootdemo.exception.CustomException;
import com.github.pateluday07.graphqlspringbootdemo.mapper.OwnerMapper;
import com.github.pateluday07.graphqlspringbootdemo.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.github.pateluday07.graphqlspringbootdemo.constant.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceImplUnitTest {

    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private OwnerMapper ownerMapper;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private OwnerServiceImpl ownerService;

    private RegistrationDto registrationDto;
    private Owner owner;

    @BeforeEach
    void registerDtoInit() {
        RegistrationDto registrationDto = new RegistrationDto();
        registrationDto.setFirstName("Uday");
        registrationDto.setLastName("Patel");
        registrationDto.setAge(26);
        registrationDto.setEmail("pateluday07@gmail.com");
        registrationDto.setPassword("123456");
        registrationDto.setConfirmPassword("123456");
        this.registrationDto = registrationDto;
    }

    @BeforeEach
    void ownerInit() {
        Owner owner = new Owner();
        owner.setFirstName("Uday");
        owner.setLastName("Patel");
        owner.setAge(26);
        owner.setEmail("pateluday07@gmail.com");
        owner.setPassword("123456");
        this.owner = owner;
    }

    @Test
    @DisplayName("Owner registration throw exception when email is invalid")
    void register_ThrowException_IfEmailIsInvalid() {
        registrationDto.setEmail("abc");
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.register(registrationDto));
        assertEquals(BAD_REQUEST_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(INVALID_EMAIL_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Owner registration throw exception if email already exists in the system")
    void register_ThrowException_IfEmailAlreadyExists() {
        when(ownerRepository.existsByEmail(anyString())).thenReturn(true);
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.register(registrationDto));
        assertEquals(BAD_REQUEST_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(EMAIL_ALREADY_EXISTS_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Owner registration throw exception if confirm password doesn't match")
    void register_ThrowException_IfConfirmPasswordDoesNotMatch() {
        registrationDto.setConfirmPassword("1");
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.register(registrationDto));
        assertEquals(BAD_REQUEST_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(CONFIRM_PASSWORD_DOES_NOT_MATCH_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Owner registration throw exception if password length less than default")
    void register_ThrowException_IfPasswordLengthLessThanDefaultLength() {
        registrationDto.setPassword("1234");
        registrationDto.setConfirmPassword(registrationDto.getPassword());
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.register(registrationDto));
        assertEquals(BAD_REQUEST_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(INVALID_PASSWORD_LENGTH_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Owner registration shouldn't throw exception if all data is valid")
    void register_NoException_IfAllDataIsValid() {
        when(ownerMapper.toEntity(any(RegistrationDto.class))).thenReturn(owner);
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);
        when(passwordEncoder.encode(anyString())).thenReturn("test");
        assertDoesNotThrow(() -> ownerService.register(registrationDto));
    }
}
