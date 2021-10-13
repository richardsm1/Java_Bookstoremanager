package com.rsm.bookstoremanager.publishers.service;

import com.rsm.bookstoremanager.publishers.dto.PublisherDTO;
import com.rsm.bookstoremanager.publishers.entity.Publisher;
import com.rsm.bookstoremanager.publishers.entity.PublisherMapper;
import com.rsm.bookstoremanager.publishers.exception.PublisherAlreadyExistsException;
import com.rsm.bookstoremanager.publishers.exception.PublisherNotFoundException;
import com.rsm.bookstoremanager.publishers.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublisherService {
    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private final PublisherRepository publisherRepository;

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

    public PublisherDTO findById(Long id) {
        return publisherRepository.findById(id)
                .map(publisherMapper::toDTO)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void verifyExists(String name, String code) {
        Optional<Publisher> duplicatepublisher = publisherRepository.findByNameOrCode(name, code);

        if (duplicatepublisher.isPresent()) {
            throw new PublisherAlreadyExistsException(name, code);
        }
    }

}
