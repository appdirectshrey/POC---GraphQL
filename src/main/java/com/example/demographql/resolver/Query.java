package com.example.demographql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.demographql.model.Author;
import com.example.demographql.model.Book;
import com.example.demographql.repository.AuthorRepository;
import com.example.demographql.repository.BookRepository;

public class Query implements GraphQLQueryResolver {

  private BookRepository bookRepository;
  private AuthorRepository authorRepository;

  public Query(AuthorRepository authorRepository, BookRepository bookRepository) {
    this.authorRepository = authorRepository;
    this.bookRepository = bookRepository;
  }

  public Book findBookById(long id) {return bookRepository.findById(id).get();}

  public Iterable<Book> findAllBooks() {
    return bookRepository.findAll();
  }

  public Iterable<Author> findAllAuthors() {
    return authorRepository.findAll();
  }

  public long countBooks() {
    return bookRepository.count();
  }
  public long countAuthors() {
    return authorRepository.count();
  }

}
