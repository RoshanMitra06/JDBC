package com.example.jdbc;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class BookController {

    //this creates a table
    @PostMapping("/createTable")
    public void createTable(@RequestParam(value = "table_name") String name) throws SQLException{
        DBOperations.createTable(name);
    }

    //returns the list of books
    @GetMapping("/getbooks")
    public List<Book> getbooks() throws SQLException {
        return DBOperations.getBooks();
    }

    //creating a book with valid credentials
    @PostMapping("/createbook")
    public void createBook(@RequestBody Book b) throws SQLException{
        DBOperations.createBook(b);
    }

    //given an id, this returns a Book. Case when the given id is not present we will still get 200 as request is correct
    @GetMapping("/getBookbyId")
    public Book getBook(@RequestParam(value = "id") int id) throws SQLException{
        return DBOperations.getBookById(id);
    }


}
