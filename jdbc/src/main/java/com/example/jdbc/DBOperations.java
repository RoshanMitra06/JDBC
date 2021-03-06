package com.example.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOperations {

    public static java.sql.Connection connection;

    public static void getConnection() throws SQLException {
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/books","root","QWERTY69");
    }

    //creates table in mysql
    public static void createTable(String name) throws SQLException{
        getConnection();

        Statement statement=connection.createStatement();

        boolean res=statement.execute("CREATE TABLE "+ name+"(id INT PRIMARY KEY AUTO_INCREMENT,book_name VARCHAR(30),"
        +"author_name VARCHAR(30), cost INT)");

        if(res){
            System.out.println("Table "+name+" has been created successfully");
        }
        else System.out.println("Table "+name+" is not created");

        connection.close();
    }

    public static void createBook(Book book) throws SQLException{
        //if taking table name in parameter check weather its present or not. If not create it
        getConnection();

        PreparedStatement statement=connection.prepareStatement("INSERT INTO my_books(book_name,author_name,cost)"
            + "VALUES (?,?,?)");

        //manual mapping. This is where ORM frameworks stands out.
        statement.setString(1,book.getBook_name());
        statement.setString(2,book.getAuthor_name());
        statement.setInt(3,book.getCost());

        int rows_affected=statement.executeUpdate();

        if(rows_affected>0){
            System.out.println(rows_affected+" rows are affected");
        }
        else System.out.println("Sorry unable to insert");

        connection.close();


    }

    //returns the list of books
    public  static List<Book> getBooks() throws SQLException{
        getConnection();

        Statement statement=connection.createStatement();

        ResultSet rs=statement.executeQuery("select * from my_books");

        List<Book> list=new ArrayList<>();
        while (rs.next()){
            int id=rs.getInt(1);
            String book_name=rs.getString(2);
            String author_name=rs.getString(3);
            int cost=rs.getInt(4);

            Book b=new Book(id,book_name,author_name,cost);
            list.add(b);

        }
        return list;
    }


    public static Book getBookById(int id) throws SQLException{

        getConnection();

        Statement statement=connection.createStatement();

        ResultSet rs=statement.executeQuery("select * from my_books where id="+id);

        Book b=null;
        while (rs.next()){
            int bid=rs.getInt(1);
            String book_name=rs.getString(2);
            String author_name=rs.getString(3);
            int cost=rs.getInt(4);

            b=new Book(id,book_name,author_name,cost);
        }

        connection.close();
        return b;
    }

}
