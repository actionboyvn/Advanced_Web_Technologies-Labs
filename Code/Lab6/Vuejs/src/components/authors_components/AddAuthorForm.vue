<template>
    <div id="add-author-form">
        <form  @submit.prevent="handleSubmit">
            <label>Author's name</label>
            <input id = "addName"
                v-model="author.name"
                type="text"
                :class="{ 'has-error': submitting && invalidName }" 
                @focus="clearStatus"
                @keypress="clearStatus"
            />
            <label>Author's surname</label>
            <input
                v-model="author.surname"
                type="text"
                :class="{ 'has-error': submitting && invalidSurname }"
                @focus="clearStatus"
            />
            <p v-if="error && submitting" class="error-message">
                Please fill in every field
            </p>
            <p v-if="success" class="success-message">
                Author added
            </p>
            <button>Add author</button>
        </form>
    </div>
</template>
<script>
    export default {
    name: 'add-author-form',
    data() {
        return {
            submitting: false,
            error: false,
            success: false, 
            author: {
                name: '',
                surname: '',
            },
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
            this.addAuthor(this.author) 
            //clear form fields
            this.author = {
                name: '',
                surname: '',
            } 
            this.error = false
            this.success = true
            this.submitting = false
        },
        clearStatus() {
            this.success = false
            this.error = false
        },
        addAuthor(author) {
            const requestOptions = {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(author)                
            };
            const response = fetch("http://localhost:9090/authors", requestOptions)
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
