package com.example.demographql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@Document
@Builder
public class Book {
    @Id
    private long id;

    @Field(name="book_title")
    private String title;

    @Field(name="book_pageCount")
    private int pageCount;

    private Author author;

    public Book() {
    }

    public Book(String title, int pageCount, Author author) {
        this.title = title;
        this.pageCount = pageCount;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    /*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }*/

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pageCount=" + pageCount +
                ", author=" + author +
                '}';
    }
}
