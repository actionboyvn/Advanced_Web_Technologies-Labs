package pl.edu.pwr.ztw.books.Repository;

import pl.edu.pwr.ztw.books.Models.Book;
import pl.edu.pwr.ztw.books.Models.Dtos.CreateBookDto;
import pl.edu.pwr.ztw.books.Models.Dtos.GetBookDto;
import pl.edu.pwr.ztw.books.Models.Dtos.GetBooksPaged;

import java.util.Collection;

public interface IBooksService {
    GetBookDto getGetBookDto(int id);
    GetBooksPaged getGetBooksDtosPaged(int pageNumber, int pageSize);
    Collection<Book> getBooks();
    int addBook(CreateBookDto createBook);
    int updateBook(Book book);
    void deleteBook(int id);
    void deleteAuthorsBooks(int authorId);
}
