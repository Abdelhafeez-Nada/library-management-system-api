package org.abdelhafeez.librarymanagementsystemapi.service.impl;

import org.abdelhafeez.librarymanagementsystemapi.repo.BookRepo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl {

    private final BookRepo bookRepo;
}
