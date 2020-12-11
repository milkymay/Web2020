<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index v-if="page === 'Index'" :users="users" :posts="posts" :comments="comments"/>
            <PostPage v-if="page === 'PostPage'" :users="users" :post="post" :comments="comments"/>
            <Enter v-if="page === 'Enter'"/>
            <Users v-if="page === 'Users'" :users="users"/>
            <Register v-if="page === 'Register'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "@/components/sidebar/Sidebar";
import Index from "@/components/middle/Index";
import PostPage from "@/components/middle/PostPage";
import Enter from "@/components/middle/Enter";
import Users from "@/components/middle/Users";
import Register from "@/components/middle/Register";
import WritePost from "@/components/middle/WritePost";
import EditPost from "@/components/middle/EditPost";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index"
        }
    },
    components: {
        WritePost,
        Enter,
        Register,
        Index,
        PostPage,
        Users,
        Sidebar,
        EditPost
    },
    props: ["posts", "users", "comments"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
        this.$root.$on("onChangePage", (page) => this.page = page);
        this.$root.$on("onShowPost", (post) => {
            this.post = post;
            this.$root.$emit("onChangePage", "PostPage");
        })
    }
}
</script>

<style scoped>

</style>