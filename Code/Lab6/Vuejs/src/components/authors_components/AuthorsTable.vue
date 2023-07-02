<template>
    <div id="authors-table">
    <br>
    <button type="button" @click="addAuthor">
        Add a new author
    </button>
    <div style="display: flex; gap:10px;">
        <label for="maxPerPage">Number of authors per page: </label>
        <input type="number" id="maxPerPage" name="maxPerPage" min="1" max="100" style="width: 70px">
        <button type="submit" @click="setMaxPerPage()">Apply</button>
    </div>
    <nav aria-label="Page navigation example">
        <ul class="pagination" style="gap:10px">       
            <li class="page-item">
                <a type = "button" class="page-link" @click="getAuthors(0)">&#60;&#60;Previous</a>
            </li>    
            <li class="page-item">
                <a type = "button" class="page-link" @click="getAuthors(1)">Next&#62;&#62;</a>
            </li>          
        </ul>
    </nav>
    <p>Current table page: {{ this.currentPageNum }}</p>
    <table>
    <thead>
        <tr>
        <th>Name</th>
        <th>Surname</th>
        <th></th>
        </tr>
    </thead>
    <tbody>
        <tr v-for="author in authorsDTO.authors" :key="author.id">
        <td>{{ author.name }}</td>
        <td>{{ author.surname }}</td>
        <td><button type = "button" class = "badge badge-success" @click="editAuthor(author.id)">Edit</button>
            <button type = "button" class="badge badge-danger mr-2" @click="deleteAuthor(author.id)">Delete</button>
        </td>
        </tr>
    </tbody>
    </table>
    
 </div>
</template>
<script>
    export default {
        name: 'authors-table',
        data() {
            return {
                currentPageNum: 1,
                maxPerPage: 5,
                authorsDTO: {
                    authors: [],
                    pageNumber: '',
                    pageSize: '',
                    totalPagesNumber: '',
                    totalItemsNumber: ''
            }
        }
        },
        methods: {
            async addAuthor() {
                this.$router.push({ name: 'AddAuthor'})
            }, 
            setMaxPerPage() {
                if(document.getElementById("maxPerPage").value < 1)
                {
                    document.getElementById("maxPerPage").value = 1;
                }
                this.maxPerPage = document.getElementById("maxPerPage").value;
                this.getAuthors(0)
            },
            async getAuthors(direction) {
                if (direction == 0) {
                    if (this.currentPageNum > 1)
                        this.currentPageNum--
                } 
                if (direction == 1)
                    this.currentPageNum++

                try {
                const response = await fetch('http://localhost:9090/authors/?' + new URLSearchParams({pageNumber : this.currentPageNum, pageSize : this.maxPerPage}), {
                    method: 'GET',
                    header: {
                        accept: 'application/json',                    
                    },
                })
                const data = await response.json()                 
                if (data.authors.length == 0 && direction == 1)
                    this.currentPageNum--    
                else {
                    this.authorsDTO = data
                } 
                }
                catch (error) {
                console.error(error)
                }
            }, 
            async editAuthor(authorID) {
                this.$router.push({ name: 'EditAuthor', params: { id: authorID } })
            }, 
            async deleteAuthor(id){
                if (window.confirm('Are you sure want to delete this author?')) {
                    try {
                    await fetch('http://localhost:9090/authors/' + id, {
                        method: 'DELETE',
                        header: {
                            accept: 'application/json',
                        },
                    })
                    this.getAuthors();
                    } catch (error) {
                    console.error(error)
                    }
                }
            },   
        },
        mounted() {
            this.getAuthors(0)
            document.getElementById("maxPerPage").value = this.maxPerPage
        }, 
 } 
</script>
<style scoped></style>
