package pl.edu.pwr.ztw.books.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.books.Models.Author;
import pl.edu.pwr.ztw.books.Models.Dtos.CreateAuthorDto;
import pl.edu.pwr.ztw.books.Models.Dtos.CreateBookDto;
import pl.edu.pwr.ztw.books.Repository.IAuthorsService;
import pl.edu.pwr.ztw.books.Repository.IBooksService;

@CrossOrigin
@RestController
public class AuthorsController {
    @Autowired
    IAuthorsService authorsService;
    @Autowired
    IBooksService booksService;

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public ResponseEntity<Object> getAuthors(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return new ResponseEntity<>(authorsService.getAuthorsPaged(pageNumber, pageSize), HttpStatus.OK);
    }
    @RequestMapping(value = "/authors/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAuthor(@PathVariable("id") int id){
        return new ResponseEntity<>(authorsService.getAuthor(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    public ResponseEntity<Object> addAuthor(@RequestBody CreateAuthorDto authorDto) {
        return new ResponseEntity<>(authorsService.addAuthor(authorDto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/authors", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateAuthor(@RequestBody Author author) {
        authorsService.updateAuthor(author);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/authors/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteAuthor(@PathVariable("id") int id) {
        authorsService.deleteAuthor(id);
        booksService.deleteAuthorsBooks(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
