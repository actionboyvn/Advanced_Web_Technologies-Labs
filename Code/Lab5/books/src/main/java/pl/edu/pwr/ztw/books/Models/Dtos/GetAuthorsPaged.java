package pl.edu.pwr.ztw.books.Models.Dtos;

import pl.edu.pwr.ztw.books.Models.Author;

import java.util.Collection;

public class GetAuthorsPaged {
    public Collection<Author> authors;
    public int pageNumber;
    public int pageSize;
    public int totalPagesNumber;
    public int totalItemsNumber;

    public GetAuthorsPaged(Collection<Author> authors, int pageNumber, int pageSize, int totalPagesNumber, int totalItemsNumber) {
        this.authors = authors;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPagesNumber = totalPagesNumber;
        this.totalItemsNumber = totalItemsNumber;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPagesNumber() {
        return totalPagesNumber;
    }

    public void setTotalPagesNumber(int totalPagesNumber) {
        this.totalPagesNumber = totalPagesNumber;
    }

    public int getTotalItemsNumber() {
        return totalItemsNumber;
    }

    public void setTotalItemsNumber(int totalItemsNumber) {
        this.totalItemsNumber = totalItemsNumber;
    }
}
