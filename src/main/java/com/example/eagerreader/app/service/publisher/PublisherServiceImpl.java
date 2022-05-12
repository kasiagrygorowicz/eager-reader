package com.example.eagerreader.app.service.publisher;

import com.example.eagerreader.app.domain.entity.Author;
import com.example.eagerreader.app.domain.entity.Publisher;
import com.example.eagerreader.app.domain.repository.PublisherRepository;
import com.example.eagerreader.app.dto.publisher.CreatePublisherDTO;
import com.example.eagerreader.app.dto.publisher.EditPublisherDTO;
import com.example.eagerreader.app.dto.publisher.PublisherDTO;
import com.example.eagerreader.app.exception.authorException.author.AuthorNotFoundException;
import com.example.eagerreader.app.exception.authorException.publisher.DuplicatePublisherException;
import com.example.eagerreader.app.service.author.AuthorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    @Override
    public void addPublisher(CreatePublisherDTO publisher) {
        findPublisherByName(publisher.getName());
        Publisher newPublisher = PublisherMapper.map(publisher);
        publisherRepository.saveAndFlush(newPublisher);
    }

    @Override
    public List<PublisherDTO> getAllPublishers() {
        return publisherRepository.findAll().stream().map(p -> {
            return PublisherMapper.map(p);
        }).collect(Collectors.toList());
    }

    @Override
    public EditPublisherDTO getPublisherToEdit(Long id) {
        Publisher publisher = findPublisherById(id);
        return PublisherMapper.map2(publisher);
    }

    @Override
    public void editPublisher(EditPublisherDTO publisher, Long id) {
        Publisher oldPublisher = findPublisherById(id);
        if (!(oldPublisher.getName().equals(publisher.getName()))) findPublisherByName(publisher.getName());

        oldPublisher.setName(publisher.getName());
        oldPublisher.setLocation(publisher.getLocation());

        oldPublisher.setFounded(publisher.getFounded().isBlank() ? null : Long.parseLong(publisher.getFounded()));
        publisherRepository.save(oldPublisher);
    }

    private void findPublisherByName(String name) {
        publisherRepository.findByName(name).ifPresent(
                publisher -> {
                    throw new DuplicatePublisherException("Publisher with the name " + name + " already exists");
                }
        );
    }

    private Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow(
                () -> new AuthorNotFoundException("Publisher with the id " + id + " does not exists")
        );
    }

    private class PublisherMapper {

        private static Publisher map(CreatePublisherDTO publisher) {
            return new Publisher(publisher.getName(), publisher.getLocation(), Long.parseLong(publisher.getFounded()));
        }

        private static PublisherDTO map(Publisher publisher) {
            return new PublisherDTO(publisher.getId(), publisher.getName(), publisher.getLocation(), publisher.getFounded()==null ?null :publisher.getFounded().toString());
        }

        private static EditPublisherDTO map2(Publisher publisher) {
            return new EditPublisherDTO(publisher.getName(), publisher.getLocation(),publisher.getFounded().toString());
        }
    }
}
