<template>
    <div id="edit-book-form">
    <form  @submit.prevent="handleSubmit">
        <label>Title</label>
        <input id = "title"
            v-model="book.title"
            type="text"
            :class="{ 'has-error': submitting && invalidTitle }" 
            @focus="clearStatus"
            @keypress="clearStatus"
        />
        <label>Author's ID</label>
        <input id = "authorID"
            v-model="book.authorId"
            type="number"
            :class="{ 'has-error': submitting && invalidID }"
            @focus="clearStatus"
        />
        <label>Pages</label>
        <input id = "pages"
            v-model="book.pages"
            type="number"
            :class="{ 'has-error': submitting && invalidPages }"
            @focus="clearStatus"
            @keypress="clearStatus"
        />
        <p v-if="error && submitting" class="error-message">
            Please fill in every field
        </p>
        <p v-if="error_no_author && !success" class="error-message">
            Author not found
        </p>
        <p v-if="success" class="success-message">
            Book updated
        </p>
        <button>Update book</button>
    </form>
    <router-link to="/books">Return to books</router-link>
 </div>
</template>
<script>
    export default {
    name: 'add-form',
    data() {
        return {
            editingBookId: 1,
            editingBook: '',
            submitting: false,
            error: false,
            error_no_author: false,
            success: false, 
            book: {
                id: '',
                title: '',
                authorId: '',
                pages: '',
            },
            bookGetDTO: {
                booksDtos: [],
                pageNumber: '',
                pageSize: '',
                totalPagesNumber: '',
                totalItemsNumber: ''
            }
    // Add other book properties here
        }        
    },
    methods: {
        handleSubmit() {
            this.submitting = true
            this.clearStatus()
            //check form fields
            if (this.invalidTitle || this.invalidID || this.invalidPages) {
                this.error = true
                return
            } 
            this.editBook(this.book) 
            //clear form fields
            if (this.success == false)
                this.book = {
                    id: '',
                    title: '',
                    authorId: '',
                    pages: '',
                } 
            this.error = false
            this.submitting = false
        },
        clearStatus() {
            this.success = false
            this.error = false
            this.error_no_author = false
        },
        async fillBookData() {
            const response = await fetch('http://localhost:9090/book/' + this.editingBookId, {
                    method: 'GET',
                    header: {
                        accept: 'application/json',                    
                    },
                })
                const data = await response.json()
                this.editingBook = data
            document.getElementById("title").value = this.editingBook.title
            document.getElementById("authorID").value = this.editingBook.author.id
            document.getElementById("pages").value = this.editingBook.pages
            this.book.id = this.editingBook.id
            this.book.title = this.editingBook.title
            this.book.authorId = this.editingBook.author.id
            this.book.pages = this.editingBook.pages
        },
        editBook(book) {
            this.success = true
            const requestOptions = {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(book)                
            };
            const response = fetch("http://localhost:9090/books", requestOptions)
                .then(response => 
                {
                    console.log(response.status);
                    if (response.status == 400) {
                        this.error_no_author = true;
                        this.success = false;
                    }
                    response.json();
                })
                .then(data => (this.updatedAt = data.updatedAt));
            console.log(response.status);
        }
    },
    computed: {
        invalidTitle() {
            return this.book.title === ''
        },
        invalidID() {
            return this.book.authorId === ''
        },
        invalidPages() {
            return this.book.pages === ''
        }
    },
    mounted() {
        this.editingBookId = this.$route.params.id       
        this.fillBookData()
        //document.getElementById("addTitle").value = "DKM"
    }, 
 }
</script>
<style scoped>
    form {
        margin-bottom: 2rem;
    }

    [class*='-message'] {
        font-weight: 500;
    }
    .error-message {
        color: #d33c40;
    }
    .success-message {
        color: #32a95d;
    }
</style>