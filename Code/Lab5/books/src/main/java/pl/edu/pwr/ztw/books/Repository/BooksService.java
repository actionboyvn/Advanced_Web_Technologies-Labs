package pl.edu.pwr.ztw.books.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwr.ztw.books.Models.Author;
import pl.edu.pwr.ztw.books.Models.Book;
import pl.edu.pwr.ztw.books.Models.Dtos.CreateBookDto;
import pl.edu.pwr.ztw.books.Models.Dtos.GetBookDto;
import pl.edu.pwr.ztw.books.Models.Dtos.GetBooksPaged;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BooksService implements IBooksService {
    @Autowired
    IAuthorsService authorsService;
    private static List<Book> booksRepo = new ArrayList<>();
    static {
        booksRepo.add(new Book(1,"Potop", 1, 936));
        booksRepo.add(new Book(2,"Wesele", 2, 150));
        booksRepo.add(new Book(3,"Dziady", 3, 292));
    }
    private static int maxIdEver = 3;

    private GetBookDto mapBookToGetBookDto(Book book){
        Author author = authorsService.getAuthor(book.getAuthorId());
        return new GetBookDto(book.getId(), book.getTitle(), author, book.getPages());
    }
    @Override
    public GetBooksPaged getGetBooksDtosPaged(int pageNumber, int pageSize) {
        List<GetBookDto> responseBooks = new ArrayList<>();
        List<Book> pagedBooksRepo = booksRepo.stream()
                .skip((long) (pageNumber - 1) * pageSize)
                .limit(pageSize)
                .toList();
        for (Book book : pagedBooksRepo) {
            responseBooks.add(mapBookToGetBookDto(book));
        }
        int totalItemsNumber = booksRepo.size();
        int totalPagesNumber = (int) Math.ceil((double) totalItemsNumber / (double) pageSize);
        return new GetBooksPaged(responseBooks, pageNumber, pageSize, totalPagesNumber, totalItemsNumber);
    }

    @Override
    public Collection<Book> getBooks() {
        return booksRepo;
    }
    @Override
    public int addBook(CreateBookDto createBook) {
        if(authorsService.getAllAuthors().stream().noneMatch(x -> x.getId() == createBook.getAuthorId())){
            return -1;
        }
        int newBookId = ++maxIdEver;
        Book newBook = new Book(newBookId, createBook.getTitle(), createBook.getAuthorId(), createBook.getPages());
        booksRepo.add(newBook);
        return newBookId;
    }
    @Override
    public int updateBook(Book book) {
        if(authorsService.getAllAuthors().stream().noneMatch(x -> x.getId() == book.getAuthorId())){
            return -1;
        }
        Book oldBook = getBook(book.getId());
        if(oldBook != null){
            oldBook.setTitle(book.getTitle());
            oldBook.setAuthorId(book.getAuthorId());
            oldBook.setPages(book.getPages());
        }
        return book.getAuthorId();
    }
    @Override
    public void deleteBook(int id) {
        Book book = getBook(id);
        booksRepo.remove(book);
    }
    @Override
    public void deleteAuthorsBooks(int authorId) {
        List<Book> authorsBooks = booksRepo.stream()
                .filter(x -> x.getAuthorId() == authorId)
                .toList();

        for (int i = 0; i < authorsBooks.size(); i++) {
            deleteBook(authorsBooks.get(i).getId());
        }
    }
    @Override
    public GetBookDto getGetBookDto(int id) {
        Book book = getBook(id);
        return mapBookToGetBookDto(book);
    }
    private Book getBook(int id){
        return booksRepo.stream()
                .filter(b -> b.getId() == id)
                .findAny()
                .orElse(null);
    }
}