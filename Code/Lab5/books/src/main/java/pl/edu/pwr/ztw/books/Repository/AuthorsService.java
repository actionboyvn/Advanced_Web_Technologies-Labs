package pl.edu.pwr.ztw.books.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AutoPopulatingList;
import pl.edu.pwr.ztw.books.Models.Author;
import pl.edu.pwr.ztw.books.Models.Book;
import pl.edu.pwr.ztw.books.Models.Dtos.CreateAuthorDto;
import pl.edu.pwr.ztw.books.Models.Dtos.GetAuthorsPaged;
import pl.edu.pwr.ztw.books.Models.Dtos.GetBookDto;
import pl.edu.pwr.ztw.books.Models.Dtos.GetBooksPaged;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class AuthorsService implements IAuthorsService{
    private static List<Author> authorsRepo = new ArrayList<>();
    static {
        authorsRepo.add(new Author(1,"Henryk", "Sienkiewicz"));
        authorsRepo.add(new Author(2,"Stanisław", "Wyspiański"));
        authorsRepo.add(new Author(3,"Adam", "Mickiewicz"));
    }
    private static int maxIdEver = 3;

    @Override
    public Author getAuthor(int id) {
        return authorsRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Collection<Author> getAllAuthors() {
        return authorsRepo;
    }

    @Override
    public GetAuthorsPaged getAuthorsPaged(int pageNumber, int pageSize) {
        List<Author> responseAuthors = authorsRepo.stream()
                .skip((long) (pageNumber - 1) * pageSize)
                .limit(pageSize)
                .toList();
        int totalItemsNumber = authorsRepo.size();
        int totalPagesNumber = (int) Math.ceil((double) totalItemsNumber / (double) pageSize);
        return new GetAuthorsPaged(responseAuthors, pageNumber, pageSize, totalPagesNumber, totalItemsNumber);
    }
    @Override
    public int addAuthor(CreateAuthorDto createAuthor) {
        int newAuthorId = ++maxIdEver;

        Author newAuthor = new Author(newAuthorId, createAuthor.getName(), createAuthor.getSurname());
        authorsRepo.add(newAuthor);
        return newAuthorId;
    }
    @Override
    public void updateAuthor(Author author) {
        Author oldAuthor = getAuthor(author.getId());

        if(oldAuthor != null){
            oldAuthor.setName(author.getName());
            oldAuthor.setSurname(author.getSurname());
        }
    }
    @Override
    public int deleteAuthor(int id) {
        if(authorsRepo.stream().noneMatch(x -> x.getId() == id)){
            return -1;
        }
        Author author = getAuthor(id);
        authorsRepo.remove(author);
        return id;
    }
}
