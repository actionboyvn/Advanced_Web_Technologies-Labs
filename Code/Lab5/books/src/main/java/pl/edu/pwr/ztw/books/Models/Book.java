package pl.edu.pwr.ztw.books.Models;

public class Book {
    private int id;
    private String title;
    private int authorId;
    int pages;
    public Book(int id, String title, int authorId, int pages) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.pages = pages;
    }
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }
}
