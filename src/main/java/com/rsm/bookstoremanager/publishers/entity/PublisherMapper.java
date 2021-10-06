package com.rsm.bookstoremanager.publishers.entity;

import com.rsm.bookstoremanager.publishers.dto.PublisherDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {
    PublisherMapper INSTANCE = Mappers.getMapper((PublisherMapper.class));

    Publisher toModel(PublisherDTO publisherDTO);

    PublisherDTO toDTO(Publisher publisher);
}
