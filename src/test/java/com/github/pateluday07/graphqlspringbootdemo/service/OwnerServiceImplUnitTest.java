package com.github.pateluday07.graphqlspringbootdemo.service;

import com.github.pateluday07.graphqlspringbootdemo.dto.CarOwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.RegistrationDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.UpdateOwnerDto;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.github.pateluday07.graphqlspringbootdemo.constant.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
    private UpdateOwnerDto updateOwnerDto;
    private OwnerDto ownerDto;
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
        owner.setId(1L);
        owner.setFirstName("Uday");
        owner.setLastName("Patel");
        owner.setAge(26);
        owner.setEmail("pateluday07@gmail.com");
        owner.setPassword("123456");
        this.owner = owner;
    }

    @BeforeEach
    void updateOwnerDtoInit() {
        UpdateOwnerDto updateOwnerDto = new UpdateOwnerDto();
        updateOwnerDto.setId(1L);
        updateOwnerDto.setFirstName("John");
        updateOwnerDto.setLastName("Doe");
        updateOwnerDto.setAge(29);
        this.updateOwnerDto = updateOwnerDto;
    }

    @BeforeEach
    void ownerDtoInit() {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(1L);
        ownerDto.setFirstName("Uday");
        ownerDto.setLastName("Patel");
        ownerDto.setAge(26);
        ownerDto.setEmail("pateluday07@gmail.com");
        this.ownerDto = ownerDto;
    }

    @Test
    @DisplayName("Owner registration, throws exception, when email is invalid")
    void register_ThrowsException_IfEmailIsInvalid() {
        registrationDto.setEmail("abc");
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.register(registrationDto));
        assertEquals(BAD_REQUEST_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(INVALID_EMAIL_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Owner registration, throws exception, if email already exists in the system")
    void register_ThrowsException_IfEmailAlreadyExists() {
        when(ownerRepository.existsByEmail(anyString())).thenReturn(true);
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.register(registrationDto));
        assertEquals(BAD_REQUEST_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(EMAIL_ALREADY_EXISTS_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Owner registration, throws exception, if confirm password doesn't match")
    void register_ThrowsException_IfConfirmPasswordDoesNotMatch() {
        registrationDto.setConfirmPassword("1");
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.register(registrationDto));
        assertEquals(BAD_REQUEST_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(CONFIRM_PASSWORD_DOES_NOT_MATCH_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Owner registration, throws exception, if password length less than default")
    void register_ThrowsException_IfPasswordLengthLessThanDefaultLength() {
        registrationDto.setPassword("1234");
        registrationDto.setConfirmPassword(registrationDto.getPassword());
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.register(registrationDto));
        assertEquals(BAD_REQUEST_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(INVALID_PASSWORD_LENGTH_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Owner registration, shouldn't throw exception, if all data is valid")
    void register_NoException_IfAllDataIsValid() {
        when(ownerMapper.toEntity(any(RegistrationDto.class))).thenReturn(owner);
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);
        when(passwordEncoder.encode(anyString())).thenReturn("test");
        assertDoesNotThrow(() -> ownerService.register(registrationDto));
    }

    @Test
    @DisplayName("Update owner. throws exception, if owner not found by id")
    void update_ThrowsException_IfOwnerNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.update(updateOwnerDto));
        assertEquals(NOT_FOUND_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(OWNER_NOT_FOUND_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Update owner, no exception, if all data valid")
    void update_NoException_IfAllDataIsValid() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(ownerMapper.toEntity(any(UpdateOwnerDto.class))).thenReturn(owner);
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);
        when(ownerMapper.toDto(any(Owner.class))).thenReturn(new OwnerDto());
        assertDoesNotThrow(() -> ownerService.update(updateOwnerDto));
    }

    @Test
    @DisplayName("Get owner by id, owner details should match when owner is found")
    void getDtoById_DetailsShouldMatch_WhenOwnerIsFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(ownerMapper.toDto(any(Owner.class))).thenReturn(ownerDto);
        OwnerDto ownerDto = assertDoesNotThrow(() -> ownerService.getDtoById(1L));
        assertEquals(owner.getId(), ownerDto.getId());
        assertEquals(owner.getFirstName(), ownerDto.getFirstName());
        assertEquals(owner.getLastName(), ownerDto.getLastName());
        assertEquals(owner.getAge(), ownerDto.getAge());
        assertTrue(ownerDto.getCars().isEmpty());
    }

    @Test
    @DisplayName("Get owner by car id, throws exception, if owner is not found")
    void getByCarId_ThrowsException_IfOwnerNotFoundByCarId() {
        when(ownerRepository.getByCarsId(anyLong())).thenReturn(Optional.empty());
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.getByCarId(1L));
        assertEquals(NOT_FOUND_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(OWNER_NOT_FOUND_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Get owner by car id, no exception, if owner is not found")
    void getByCarId_NoException_IfOwnerIsFoundByCarId() {
        when(ownerRepository.getByCarsId(anyLong())).thenReturn(Optional.of(owner));
        when(ownerMapper.toCarOwnerDto(any(Owner.class))).thenReturn(new CarOwnerDto());
        assertDoesNotThrow(() -> ownerService.getByCarId(1L));
    }

    @Test
    @DisplayName("Get all owners, returns empty list, when owners are not found")
    void getAll_EmptyList_IfOwnersNotFound() {
        when(ownerRepository.findAll()).thenReturn(Collections.emptyList());
        List<OwnerDto> owners = assertDoesNotThrow(() -> ownerService.getAll());
        assertTrue(owners.isEmpty());
    }

    @Test
    @DisplayName("Get all owners, returns a list of owners, when owners are found")
    void getAll_NonEmptyList_WhenOwnersFound() {
        List<Owner> ownersToMock = Arrays.asList(owner, owner, owner, owner);
        when(ownerRepository.findAll()).thenReturn(ownersToMock);
        when(ownerMapper.toDto(any(Owner.class))).thenReturn(ownerDto);
        List<OwnerDto> owners = assertDoesNotThrow(() -> ownerService.getAll());
        assertEquals(ownersToMock.size(), owners.size());
    }

    @Test
    @DisplayName("Delete by id, throws exception, when owner not found")
    void deleteById_ThrowsException_IfOwnerNotFound() {
        when(ownerRepository.existsById(anyLong())).thenReturn(false);
        CustomException customException = assertThrows(CustomException.class, () -> ownerService.deleteById(1L));
        assertEquals(NOT_FOUND_ERROR_CODE, customException.getExtensions().get(EXTENSION_ERROR_CODE_KEY));
        assertEquals(OWNER_NOT_FOUND_ERROR_MSG, customException.getExtensions().get(EXTENSION_ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Delete by id, delete owner, when owner found")
    void deleteById_DeleteOwner_IfOwnerFound() {
        when(ownerRepository.existsById(anyLong())).thenReturn(true);
        assertDoesNotThrow(() -> ownerService.deleteById(1L));
    }

}
