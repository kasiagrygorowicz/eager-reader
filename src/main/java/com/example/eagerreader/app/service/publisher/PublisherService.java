package com.example.eagerreader.app.service.publisher;

import com.example.eagerreader.app.dto.publisher.CreatePublisherDTO;
import com.example.eagerreader.app.dto.publisher.EditPublisherDTO;
import com.example.eagerreader.app.dto.publisher.PublisherDTO;

import java.util.List;

public interface PublisherService {

    void addPublisher(CreatePublisherDTO publisher);
    List<PublisherDTO> getAllPublishers();
    EditPublisherDTO getPublisherToEdit(Long id);
    void editPublisher(EditPublisherDTO publisher, Long id);
}
