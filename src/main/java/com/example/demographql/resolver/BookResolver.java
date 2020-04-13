package com.example.demographql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.demographql.model.Author;
import com.example.demographql.model.Book;
import com.example.demographql.repository.AuthorRepository;

public class BookResolver implements GraphQLResolver<Book> {

  private AuthorRepository authorRepository;

  public BookResolver(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public Author getAuthor(Book book) {
    return authorRepository.findById(book.getAuthor().getId()).get();
  }

}
