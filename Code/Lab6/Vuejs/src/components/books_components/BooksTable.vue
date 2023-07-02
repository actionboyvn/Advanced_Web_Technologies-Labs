<template>
    <div id="books-table">
    <br>
    <button type="button" @click="addBook">
        Add a new book
    </button>
    <div style="display: flex; gap:10px;">
        <label for="maxPerPage">Number of books per page: </label>
        <input type="number" id="maxPerPage" name="maxPerPage" min="1" max="100" style="width: 70px">
        <button type="submit" @click="setMaxPerPage()">Apply</button>
    </div>
    <nav aria-label="Page navigation example">
        <ul class="pagination" style="gap:10px">       
            <li class="page-item">
                <a type = "button" class="page-link" @click="getBooks(0)">&#60;&#60;Previous</a>
            </li>    
            <li class="page-item">
                <a type = "button" class="page-link" @click="getBooks(1)">Next&#62;&#62;</a>
            </li>          
        </ul>
    </nav>
    <p>Current table page: {{ this.currentPageNum }}</p>
    <table>
    <thead>
        <tr>
        <th>Title</th>
        <th>Author's name</th>
        <th>Author's surname</th>
        <th>Pages</th>
        <th></th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="book in bookGetDTO.booksDtos" :key="book.id">
        <td>{{ book.title }}</td>
        <td>{{ book.author.name }}</td>
        <td>{{ book.author.surname }}</td>
        <td>{{ book.pages }}</td>
        <td><button type = "button" class = "badge badge-success" @click="editBook(book.id)">Edit</button>
            <button type = "button" class="badge badge-danger mr-2" @click="deleteBook(book.id)">Delete</button>
        </td>
        </tr>
    </tbody>
    </table>
    
 </div>
</template>
<script>
    export default {
        name: 'books-table',
        data() {
            return {
                currentPageNum: 1,
                maxPerPage: 5,
                books: [],
                bookGetDTO: {
                    booksDtos: [],
                    pageNumber: '',
                    pageSize: '',
                    totalPagesNumber: '',
                    totalItemsNumber: ''
            }
        }
        },
        methods: {
            async addBook() {
                this.$router.push({ name: 'AddBook'})
            }, 
            setMaxPerPage() {
                if(document.getElementById("maxPerPage").value < 1)
                {
                    document.getElementById("maxPerPage").value = 1;
                }
                this.maxPerPage = document.getElementById("maxPerPage").value;
                this.getBooks(0)
            },
            async getBooks(direction) {
                if (direction == 0) {
                    if (this.currentPageNum > 1)
                        this.currentPageNum--
                } 
                if (direction == 1)
                    this.currentPageNum++

                try {
                const response = await fetch('http://localhost:9090/books/?' + new URLSearchParams({pageNumber : this.currentPageNum, pageSize : this.maxPerPage}), {
                    method: 'GET',
                    header: {
                        accept: 'application/json',                    
                    },
                })
                const data = await response.json()                 
                if (data.booksDtos.length == 0 && direction == 1)
                    this.currentPageNum--    
                else {
                    this.bookGetDTO = data
                } 
                }
                catch (error) {
                console.error(error)
                }
            }, 
            async editBook(bookID) {
                this.$router.push({ name: 'EditBook', params: { id: bookID } })
            }, 
            async deleteBook(id){
                if (window.confirm('Are you sure want to delete this book?')) {
                    try {
                    await fetch('http://localhost:9090/books/' + id, {
                        method: 'DELETE',
                        header: {
                            accept: 'application/json',
                        },
                    })
                    this.getBooks();
                    } catch (error) {
                    console.error(error)
                    }
                }
            },   
        },
        mounted() {
            this.getBooks(0)
            document.getElementById("maxPerPage").value = this.maxPerPage
        }, 
 } 
</script>
<style scoped></style>
