<template>
    <div id="add-book-form">
    <form  @submit.prevent="handleSubmit">
        <label>Title</label>
        <input id = "addTitle"
            v-model="book.title"
            type="text"
            :class="{ 'has-error': submitting && invalidTitle }" 
            @focus="clearStatus"
            @keypress="clearStatus"
        />
        <label>Author's ID</label>
        <input
            v-model="book.authorId"
            type="number"
            :class="{ 'has-error': submitting && invalidID }"
            @focus="clearStatus"
        />
        <label>Pages</label>
        <input
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
            Book added
        </p>
        <button>Add book</button>
    </form>
 </div>
</template>
<script>
    export default {
    name: 'add-form',
    data() {
        return {
            submitting: false,
            error: false,
            error_no_author: false,
            success: false, 
            book: {
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
            this.addBook(this.book) 
            //clear form fields
            this.book = {
                title: '',
                authorId: '',
                pages: '',
            } 
            this.error = false
            this.success = true
            this.submitting = false
        },
        clearStatus() {
            this.success = false
            this.error = false
        },
        addBook(book) {
            const requestOptions = {
                method: "POST",
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
                    else{
                        console.log("bbb");
                    }
                    response.json();
                })
                .then(data => (this.updatedAt = data.updatedAt));
            console.log(response.status);
            if (response.status == 400) {
                console.log("aaa");
            }
            else{
                console.log("bbb");
            }
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