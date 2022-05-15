package com.example.eagerreader.app.service;

import com.example.eagerreader.app.service.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExportFavoritesServiceImpl {

    private final AuthorService authorService;




}
