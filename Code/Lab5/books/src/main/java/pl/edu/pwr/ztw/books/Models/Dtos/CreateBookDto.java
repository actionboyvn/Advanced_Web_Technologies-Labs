package pl.edu.pwr.ztw.books.Models.Dtos;

public class CreateBookDto {
    private String title;
    private int authorId;
    int pages;

    public CreateBookDto(String title, int authorId, int pages) {
        this.title = title;
        this.authorId = authorId;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
