import { createSchema, createYoga } from 'graphql-yoga'
import { createServer } from 'node:http'
import axios from 'axios'
import { group } from 'node:console';
const yoga = createYoga({
    schema: createSchema({
        typeDefs: /* GraphQL */ `
        type Query {
            todos: [ToDoItem!]
            todo(id: ID!): ToDoItem
            users: [User!]
            user(id: ID!): User
        }
        type ToDoItem{
            user: User!
            id: ID!
            title: String!
            completed: Boolean!
        }
        type User{
            id: ID!
            name: String!
            email: String!
            login: String!
            todos: [ToDoItem!]!
        } 
    `,
        resolvers: {
            Query: {
                users: async () => getRestUsersList(),//, getRestUsersList()
                todos: async () => getRestTodosList(), //, todosList() => todosList,//
                todo: (parent, args, context, info) => todoById(parent, args, context, info),
                user: (parent, args, context, info) => userById(parent, args, context, info),
            }
        },
        User: {
            todos: (parent, args, context, info) => userById(parent, args, context, info),
        },
        ToDoItem: {
            user: (parent, args, context, info) => todoById(parent, args, context, info)
        },

    })
})
const server = createServer(yoga)
server.listen(4000, () => {
    console.info('Server is running on http://localhost:4000/graphql')
})
// const usersList = [
//     { id: 1, name: "Jan Konieczny", email: "jan.konieczny@wonet.pl", login: "jkonieczny" },
//     { id: 2, name: "Anna Wesołowska", email: "anna.w@sad.gov.pl", login: "anna.wesolowska" },
//     { id: 3, name: "Piotr Waleczny", email: "piotr.waleczny@gp.pl", login: "p.waleczny" }
//    ]; 
// const todosList = [
//     { id: 1, title: "Naprawić samochód", completed: false, userid: 3 },
//     { id: 2, title: "Posprzątać garaż", completed: true, userid: 3 },
//     { id: 3, title: "Napisać e-mail", completed: false, userid: 3 },
//     { id: 4, title: "Odebrać buty", completed: false, userid: 2 },
//     { id: 5, title: "Wysłać paczkę", completed: true, userid: 2 },
//     { id: 6, title: "Zamówic kuriera", completed: false, userid: 3 },
//    ]; 
async function todoById(parent, args, context, info) {
    const todos = await getRestTodosList()
    const todo = todos.find(t => t.id == args.id);
    return todo;
}
async function userById(parent, args, context, info) {
    const users = await getRestUsersList()
    const user = users.find(u => u.id == args.id)
    return user;
}
async function getRestUsersList() {
    try {
        const users = await axios.get("https://jsonplaceholder.typicode.com/users")
        const todos = await axios.get("https://jsonplaceholder.typicode.com/todos")
        var todos_mapped = todos.data.map(({ userId, id, title, completed }) => ({
            userid: userId,
            id: id,
            title: title,
            completed: completed,
        }))
        return users.data.map(({ id, name, email, username }) => ({
            id: id,
            name: name,
            email: email,
            login: username,
            todos: todos_mapped.filter(t => t.userid == id)
        }))
    } catch (error) {
        throw error
    }
}
async function getRestTodosList() {
    try {
        const todos = await axios.get("https://jsonplaceholder.typicode.com/todos")
        const users = await axios.get("https://jsonplaceholder.typicode.com/users")
        var users_mapped = users.data.map(({ id, name, email, username }) => ({
            id: id,
            name: name,
            email: email,
            login: username
        }))
        return todos.data.map(({ userId, id, title, completed }) => ({            
            user: users_mapped.find(t => t.id == userId),
            id: id,
            title: title,
            completed: completed,
        }))
    } catch (error) {
        throw error
    }
}
