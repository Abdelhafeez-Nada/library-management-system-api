package org.abdelhafeez.librarymanagementsystemapi.web.controller;

import org.abdelhafeez.librarymanagementsystemapi.service.contract.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
}
