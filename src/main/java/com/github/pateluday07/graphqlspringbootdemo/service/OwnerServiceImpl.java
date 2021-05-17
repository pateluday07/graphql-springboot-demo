package com.github.pateluday07.graphqlspringbootdemo.service;

import com.github.pateluday07.graphqlspringbootdemo.constant.Constants;
import com.github.pateluday07.graphqlspringbootdemo.dto.CarOwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.OwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.RegistrationDto;
import com.github.pateluday07.graphqlspringbootdemo.dto.UpdateOwnerDto;
import com.github.pateluday07.graphqlspringbootdemo.entity.Owner;
import com.github.pateluday07.graphqlspringbootdemo.exception.CustomException;
import com.github.pateluday07.graphqlspringbootdemo.mapper.CarMapper;
import com.github.pateluday07.graphqlspringbootdemo.mapper.OwnerMapper;
import com.github.pateluday07.graphqlspringbootdemo.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new CustomException(Constants.NOT_FOUND_ERROR_CODE, Constants.OWNER_NOT_FOUND_ERROR_MSG));
    }

    @Override
    public CarOwnerDto getByCarId(Long id) {
        log.info("Service method to get owner by car id: {}", id);
        return ownerMapper.toCarOwnerDto(ownerRepository.getByCarsId(id)
                .orElseThrow(() -> new CustomException(Constants.NOT_FOUND_ERROR_CODE, Constants.OWNER_NOT_FOUND_ERROR_MSG)));
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
            throw new CustomException(Constants.NOT_FOUND_ERROR_CODE, Constants.OWNER_NOT_FOUND_ERROR_MSG);
    }

    private void validateRegistrationDto(RegistrationDto registrationDto) {
        if (!EmailValidator.getInstance().isValid(registrationDto.getEmail()))
            throw new CustomException(Constants.BAD_REQUEST_ERROR_CODE, Constants.INVALID_EMAIL_ERROR_MSG);
        if (ownerRepository.existsByEmail(registrationDto.getEmail()))
            throw new CustomException(Constants.BAD_REQUEST_ERROR_CODE, Constants.EMAIL_ALREADY_EXISTS_ERROR_MSG);
        if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword()))
            throw new CustomException(Constants.BAD_REQUEST_ERROR_CODE, Constants.CONFIRM_PASSWORD_DOES_NOT_MATCH_ERROR_MSG);
        if (registrationDto.getPassword().length() < Constants.PASSWORD_LENGTH)
            throw new CustomException(Constants.BAD_REQUEST_ERROR_CODE, Constants.INVALID_PASSWORD_LENGTH_ERROR_MSG);
    }
}
