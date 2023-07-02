package pl.edu.pwr.ztw.books.Models.Dtos;

import java.util.Collection;

public class GetBooksPaged {
    public Collection<GetBookDto> booksDtos;
    public int pageNumber;
    public int pageSize;
    public int totalPagesNumber;
    public int totalItemsNumber;

    public GetBooksPaged(Collection<GetBookDto> booksDtosMod, int pageNumber, int pageSize, int totalPagesNumber, int totalItemsNumber) {
        this.booksDtos = booksDtosMod;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPagesNumber = totalPagesNumber;
        this.totalItemsNumber = totalItemsNumber;
    }

    public Collection<GetBookDto> getBooksDtosMod() {
        return booksDtos;
    }

    public void setBooksDtosMod(Collection<GetBookDto> booksDtosMod) {
        this.booksDtos = booksDtosMod;
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
