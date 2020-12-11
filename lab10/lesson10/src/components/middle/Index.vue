<template>
    <div>
        <Post v-for="post in viewPosts" :post="post" :comments="comments" :users="users" :showComments="false" :key="post.id"/>
    </div>
</template>

<script>
    import Post from "@/components/middle/Post";

    export default {
        name: "Index",
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id);
            }
        },
        components: {
            Post
        },
        props: ["posts", "users", "comments"],
    }
</script>

<style scoped>

</style>