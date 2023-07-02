<template>
  <div id="app" class="small-container">
    <nav class="navbar navbar-expand-md navbar-dark bg-dark">
      <div class="container">
        <ul class="navbar-nav mr-auto">
          <li id="BooksTab" class="nav-item" @click="setAsActiveTab(0)">
            <router-link to="/books" class="nav-link">Books</router-link>
          </li>
          <li id="AuthorsTab" class="nav-item">
            <router-link to="/authors" class="nav-link" @click="setAsActiveTab(1)">Authors</router-link>
          </li>
        </ul>
      </div>
    </nav>
    <router-view/>
  </div> 
</template>
<script> 
  export default {
    methods: {
      setAsActiveTab(tabIndex){
          if(tabIndex == 0) {
            document.getElementById("BooksTab").classList.add('active');
            document.getElementById("AuthorsTab").classList.remove('active');
          }
          else if(tabIndex == 1){          
            document.getElementById("AuthorsTab").classList.add('active');
            document.getElementById("BooksTab").classList.remove('active');
          }
      }
    },
    mounted() {
      this.setAsActiveTab(0)
    },
    name: 'app',
    }
</script>
<style>
  button {
  background: #009435;
  border: 1px solid #009435;
  }
  .small-container {
  max-width: 1000px; 
  }
</style>
