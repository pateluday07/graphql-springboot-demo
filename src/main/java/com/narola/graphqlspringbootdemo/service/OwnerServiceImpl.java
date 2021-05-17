package com.narola.graphqlspringbootdemo.service;

import com.narola.graphqlspringbootdemo.dto.CarOwnerDto;
import com.narola.graphqlspringbootdemo.dto.OwnerDto;
import com.narola.graphqlspringbootdemo.dto.RegistrationDto;
import com.narola.graphqlspringbootdemo.dto.UpdateOwnerDto;
import com.narola.graphqlspringbootdemo.entity.Owner;
import com.narola.graphqlspringbootdemo.exception.CustomException;
import com.narola.graphqlspringbootdemo.mapper.CarMapper;
import com.narola.graphqlspringbootdemo.mapper.OwnerMapper;
import com.narola.graphqlspringbootdemo.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.narola.graphqlspringbootdemo.constant.Constants.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;
    private final CarMapper carMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(RegistrationDto registrationDto) {
        log.info("Service method to register owner: {}", registrationDto);
        validateRegistrationDto(registrationDto);
        Owner owner = ownerMapper.toEntity(registrationDto);
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        owner = ownerRepository.save(owner);
        log.info("Registered owner: {}", owner);
    }

    @Override
    @Transactional
    public OwnerDto update(UpdateOwnerDto updateOwnerDto) {
        log.info("Service method to update owner: {}", updateOwnerDto);
        Owner ownerFromDb = getById(updateOwnerDto.getId());
        Owner owner = ownerMapper.toEntity(updateOwnerDto);
        owner.setEmail(ownerFromDb.getEmail());
        owner.setPassword(ownerFromDb.getPassword());
        owner = ownerRepository.save(owner);
        log.info("Updated owner: {}", owner);
        return ownerMapper.toDto(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public OwnerDto getDtoById(Long id) {
        return ownerMapper.toDto(getById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Owner getById(Long id) {
        log.info("Service method to get owner by id: {}", id);
        return ownerRepository.findById(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_ERROR_CODE, OWNER_NOT_FOUND_ERROR_MSG));
    }

    @Override
    public CarOwnerDto getByCarId(Long id) {
        log.info("Service method to get owner by car id: {}", id);
        return ownerMapper.toCarOwnerDto(ownerRepository.getByCarsId(id)
                .orElseThrow(() -> new CustomException(NOT_FOUND_ERROR_CODE, OWNER_NOT_FOUND_ERROR_MSG)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OwnerDto> getAll() {
        log.info("Service method to get all owners");
        return ownerRepository
                .findAll()
                .parallelStream()
                .map(ownerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.info("Service method to delete owner by id: {}", id);
        checkIfOwnerExistsById(id);
        ownerRepository.deleteById(id);
    }

    private void checkIfOwnerExistsById(Long id) {
        if (!ownerRepository.existsById(id))
            throw new CustomException(NOT_FOUND_ERROR_CODE, OWNER_NOT_FOUND_ERROR_MSG);
    }

    private void validateRegistrationDto(RegistrationDto registrationDto) {
        if (!EmailValidator.getInstance().isValid(registrationDto.getEmail()))
            throw new CustomException(BAD_REQUEST_ERROR_CODE, INVALID_EMAIL_ERROR_MSG);
        if (ownerRepository.existsByEmail(registrationDto.getEmail()))
            throw new CustomException(BAD_REQUEST_ERROR_CODE, EMAIL_ALREADY_EXISTS_ERROR_MSG);
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword()))
            throw new CustomException(BAD_REQUEST_ERROR_CODE, CONFIRM_PASSWORD_DOES_NOT_MATCH_ERROR_MSG);
        if (registrationDto.getPassword().length() < PASSWORD_LENGTH)
            throw new CustomException(BAD_REQUEST_ERROR_CODE, INVALID_PASSWORD_LENGTH_ERROR_MSG);
    }
}
