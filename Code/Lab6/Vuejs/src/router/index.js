import { createRouter, createWebHistory } from 'vue-router'
import BooksTable from '@/components/books_components/BooksTable.vue'
import AddBookForm from '@/components/books_components/AddBookForm.vue'
import EditBookForm from '@/components/books_components/EditBookForm.vue'
import AuthorsTable from '@/components/authors_components/AuthorsTable.vue'
import AddAuthorForm from '@/components/authors_components/AddAuthorForm.vue'
import EditAuthorForm from '@/components/authors_components/EditAuthorForm.vue'

const routes = [
  {
    path: '/books',
    name: 'Books',
    component: BooksTable
  },
  {
    path: '/books/add',
    name: 'AddBook',
    component: AddBookForm
  },
  {
    path: '/books/edit/:id',
    name: 'EditBook',
    component: EditBookForm,
    props: true
  },
  {
    path: '/authors',
    name: 'Authors',
    component: AuthorsTable
  },
  {
    path: '/authors/add',
    name: 'AddAuthor',
    component: AddAuthorForm
  },
  {
    path: '/authors/edit/:id',
    name: 'EditAuthor',
    component: EditAuthorForm,
    props: true
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
