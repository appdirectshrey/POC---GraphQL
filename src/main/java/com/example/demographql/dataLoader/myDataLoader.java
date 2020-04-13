package com.example.demographql.dataLoader;

import com.example.demographql.model.Author;
import com.example.demographql.model.Book;
import com.example.demographql.repository.AuthorRepository;
import com.example.demographql.repository.BookRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class myDataLoader {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  @Autowired
  public myDataLoader(BookRepository bookRepository,
      AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  @PostConstruct
  private void generateData(){

    System.out.println("In Data Loader Shrey!!");
    List<Book> booksList = new ArrayList<>();
    booksList.add(Book.builder().id(1).title("Book1").pageCount(100).build());
    booksList.add(Book.builder().id(2).title("Book2").pageCount(75).build());

    booksList = (ArrayList) bookRepository.saveAll(booksList);

    List<Author> authorList = new ArrayList<>();
    authorList.add(Author.builder().id(1).firstName("Raj").lastName("Kapoor").build());
    authorList.add(Author.builder().id(2).firstName("Rahul").lastName("Malhotra").build());

    authorList = (ArrayList) authorRepository.saveAll(authorList);

    booksList.get(0).setAuthor(authorList.get(0));
    booksList.get(1).setAuthor(authorList.get(1));
    bookRepository.saveAll(booksList);

  }
}
