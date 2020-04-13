package com.example.demographql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.demographql.exception.BookNotFoundException;
import com.example.demographql.model.Author;
import com.example.demographql.model.Book;
import com.example.demographql.repository.AuthorRepository;
import com.example.demographql.repository.BookRepository;

public class Mutation implements GraphQLMutationResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(Long id, String firstName, String lastName) {
        Author author = new Author();
        author.setId(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorRepository.save(author);

        return author;
    }

    public Book newBook(Long id, String title, Integer pageCount, Long authorId) {
        Book book = new Book();
        book.setId(id);
        book.setAuthor(new Author(authorId));
        book.setTitle(title);
        book.setPageCount(pageCount != null ? pageCount : 0);

        bookRepository.save(book);

        return book;
    }

    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        return true;
    }

    public Book updateTitle(String title, Long id) {
        Book book = bookRepository.findById(id).get();
        if(book == null) {
            throw new BookNotFoundException("The book to be updated was found", id);
        }
        book.setTitle(title);

        bookRepository.save(book);

        return book;
    }

    public Book updateBookPageCount(Integer pageCount, Long id) {
        Book book = bookRepository.findById(id).get();
        if(book == null) {
            throw new BookNotFoundException("The book to be updated was found", id);
        }
        book.setPageCount(pageCount);

        bookRepository.save(book);

        return book;
    }
}
