package pl.edu.pwr.ztw.books.Controllers;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.Models.Book;
import pl.edu.pwr.ztw.books.Models.Dtos.CreateBookDto;
import pl.edu.pwr.ztw.books.Models.Exceptions.CustomException;
import pl.edu.pwr.ztw.books.Repository.IAuthorsService;
import pl.edu.pwr.ztw.books.Repository.IBooksService;

@CrossOrigin
@RestController
public class BooksControler {
    @Autowired
    IBooksService booksService;
    @Autowired
    IAuthorsService authorsService;
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public ResponseEntity<Object> getBooks(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return new ResponseEntity<>(booksService.getGetBooksDtosPaged(pageNumber, pageSize), HttpStatus.OK);
        //return new ResponseEntity<>(booksService.getGetBooksDtos(), HttpStatus.OK);
    }
    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getBook(@PathVariable("id") int id){
        return new ResponseEntity<>(booksService.getGetBookDto(id), HttpStatus.OK);
    }
    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public ResponseEntity<Object> addBook(@RequestBody CreateBookDto bookDto) {
        int responseNewId = booksService.addBook(bookDto);
        if(responseNewId == -1){
            throw new CustomException("Author with id: " + bookDto.getAuthorId() + " not found");
        }
        else{
            return new ResponseEntity<>(responseNewId, HttpStatus.CREATED);
        }
    }
    @RequestMapping(value = "/books", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateBook(@RequestBody Book book) {
        int responseUpdatedId = booksService.updateBook(book);
        if (responseUpdatedId == -1) {
            throw new CustomException("Author with id " + responseUpdatedId + " not found");
        } else
            return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteBook(@PathVariable("id") int id) {
        booksService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}