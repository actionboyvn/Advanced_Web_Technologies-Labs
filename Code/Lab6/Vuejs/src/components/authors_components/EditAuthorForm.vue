<template>
    <div id="edit-author-form">
        <form  @submit.prevent="handleSubmit">
            <label>Author's name</label>
            <input id = "name"
                v-model="author.name"
                type="text"
                :class="{ 'has-error': submitting && invalidName }" 
                @focus="clearStatus"
                @keypress="clearStatus"
            />
            <label>Author's surname</label>
            <input id = "surname"
                v-model="author.surname"
                type="text"
                :class="{ 'has-error': submitting && invalidSurname }"
                @focus="clearStatus"
            />
            <p v-if="error && submitting" class="error-message">
                Please fill in every field
            </p>
            <p v-if="success" class="success-message">
                Author updated
            </p>
            <button>Update author</button>
        </form>
        <router-link to="/authors">Return to authors</router-link>
    </div>
</template>
<script>
    export default {
    name: 'edit-author-form',
    data() {
        return {
            editingAuthorId: 1,
            editingAuthor: '',
            submitting: false,
            error: false,
            success: false, 
            author: {
                id: '',
                name: '',
                surname: '',
            },
            authorDTO: {
                authors: []
            }
        }        
    },
    methods: {
        handleSubmit() {
            this.submitting = true
            this.clearStatus()
            //check form fields
            if (this.invalidName || this.invalidSurname) {
                this.error = true
                return
            } 
            this.editAuthor(this.author) 
            //clear form fields
            if (this.success == false)
                this.author = {
                    id: '',
                    name: '',
                    surname: '',
                } 
            this.error = false
            this.submitting = false
        },
        clearStatus() {
            this.success = false
            this.error = false
        },
        async fillAuthorData() {
            const response = await fetch('http://localhost:9090/authors/' + this.editingAuthorId, {
                    method: 'GET',
                    header: {
                        accept: 'application/json',                    
                    },
                })
                const data = await response.json()
                this.editingAuthor = data
            document.getElementById("name").value = this.editingAuthor.name
            document.getElementById("surname").value = this.editingAuthor.surname
            this.author.id = this.editingAuthorId
            this.author.name = this.editingAuthor.name
            this.author.surname = this.editingAuthor.surname
        },
        editAuthor(author) {
            this.success = true
            const requestOptions = {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(author)                
            };
            const response = fetch("http://localhost:9090/authors/", requestOptions)
                .then(response => 
                {
                    console.log(response.status);
                    if (response.status == 400) {
                        this.success = false;
                    }
                    response.json();
                })
                .then(data => (this.updatedAt = data.updatedAt));
            console.log(response.status);
        }
    },
    computed: {
        invalidName() {
            return this.author.name === ''
        },
        invalidSurname() {
            return this.author.surname === ''
        },
    },
    mounted() {
        this.editingAuthorId = this.$route.params.id       
        this.fillAuthorData()
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
