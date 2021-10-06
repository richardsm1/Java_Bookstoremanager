package com.rsm.bookstoremanager.publishers.service;

import com.rsm.bookstoremanager.publishers.dto.PublisherDTO;
import com.rsm.bookstoremanager.publishers.entity.Publisher;
import com.rsm.bookstoremanager.publishers.entity.PublisherMapper;
import com.rsm.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.rsm.bookstoremanager.publishers.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublisherService {
    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO) {
        verifyExists(publisherDTO.getName(), publisherDTO.getCode());
        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);
        return publisherMapper.toDTO(createdPublisher);
    }

    private void verifyExists(String name, String code) {
        Optional<Publisher> duplicatepublisher = publisherRepository.findByNameOrCode(name, code);

        if(duplicatepublisher.isPresent()){
            throw new PublisherAlreadyExistsException(name, code);
        }
    }

}
