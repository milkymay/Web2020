<template>
    <div id="app">
        <Header :userId="userId" :users="users"/>
        <Middle :posts="posts" :users="users" :comments="comments"/>
        <Footer :postsCount="postsCount" :usersCount="usersCount"/>
    </div>
</template>

<script>
    import Header from "./components/Header";
    import Middle from "./components/Middle";
    import Footer from "./components/Footer";

    export default {
        name: 'App',
        components: {
            Footer,
            Middle,
            Header
        },
        data: function () {
            return this.$root.$data;
        }, computed: {
            postsCount: function () {
                return Object.values(this.posts).length;
            },
            usersCount: function () {
                return Object.values(this.users).length;
            }
        },
        beforeCreate() {
            this.$root.$on("onEnter", (login, password) => {
                if (password === "") {
                    this.$root.$emit("onEnterValidationError", "Password is required");
                    return;
                }

                const users = Object.values(this.users).filter(u => u.login === login);
                if (users.length === 0) {
                    this.$root.$emit("onEnterValidationError", "No such user");
                } else {
                    this.userId = users[0].id;
                    this.$root.$emit("onChangePage", "Index");
                }
            });

            this.$root.$on("onRegister", (name, login, password) => {
                if (password === "") {
                    this.$root.$emit("onRegisterValidationError", "Password is required!");
                    return;
                }

                if (name === "") {
                    this.$root.$emit("onRegisterValidationError", "Name is required");
                    return;
                }

                const regex = RegExp('^[a-z]+$');
                const users = Object.values(this.users).filter(u => u.login === login);
                if (users.length !== 0) {
                    this.$root.$emit("onRegisterValidationError", "Login is already in use");
                } else if (login.length < 3 || login.length > 16) {
                    this.$root.$emit("onRegisterValidationError", "Login size must be between 3 and 16");
                } else if (!regex.test(login)) {
                    this.$root.$emit("onRegisterValidationError", "Login must contain latin lowercase letters only");
                } else if (name.length > 32) {
                    this.$root.$emit("onRegisterValidationError", "Name size must be between 1 and 32");
                } else {
                    const id = Math.max(...Object.keys(this.users)) + 1;
                    this.$root.$set(this.users, id, {
                        id, name, login
                    });
                    this.$root.$emit("onChangePage", "Enter");
                }
            });

            this.$root.$on("onLogout", () => this.userId = null);

            this.$root.$on("onWritePost", (title, text) => {
                if (this.userId) {
                    if (!title || title.length < 5) {
                        this.$root.$emit("onWritePostValidationError", "Title is too short");
                    } else if (!text || text.length < 10) {
                        this.$root.$emit("onWritePostValidationError", "Text is too short");
                    } else {
                        const id = Math.max(...Object.keys(this.posts)) + 1;
                        this.$root.$set(this.posts, id, {
                            id, title, text, userId: this.userId
                        });
                    }
                } else {
                    this.$root.$emit("onWritePostValidationError", "No access");
                }
            });

            this.$root.$on("onEditPost", (id, text) => {
                if (this.userId) {
                    if (!id) {
                        this.$root.$emit("onEditPostValidationError", "ID is invalid");
                    } else if (!text || text.length < 10) {
                        this.$root.$emit("onEditPostValidationError", "Text is too short");
                    } else {
                        let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
                        if (posts.length) {
                            posts.forEach((item) => {
                                item.text = text;
                            });
                        } else {
                            this.$root.$emit("onEditPostValidationError", "No such post");
                        }
                    }
                } else {
                    this.$root.$emit("onEditPostValidationError", "No access");
                }
            });
        }
    }
</script>

<style>
    #app {

    }
</style>
