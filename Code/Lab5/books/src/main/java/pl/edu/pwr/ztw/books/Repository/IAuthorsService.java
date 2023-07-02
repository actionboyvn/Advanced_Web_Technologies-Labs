package pl.edu.pwr.ztw.books.Repository;

import pl.edu.pwr.ztw.books.Models.Author;
import pl.edu.pwr.ztw.books.Models.Dtos.CreateAuthorDto;
import pl.edu.pwr.ztw.books.Models.Dtos.GetAuthorsPaged;

import java.util.Collection;

public interface IAuthorsService {
    Author getAuthor(int id);
    Collection<Author> getAllAuthors();
    GetAuthorsPaged getAuthorsPaged(int pageNumber, int pageSize);
    int addAuthor(CreateAuthorDto createAuthor);
    void updateAuthor(Author author);
    int deleteAuthor(int id);
}
