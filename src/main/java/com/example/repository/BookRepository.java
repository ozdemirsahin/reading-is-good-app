package com.example.repository;

import com.example.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    Optional<Book> findByNameAndWriterAndPublishYear(String name, String writer, Integer publishYear);

}
