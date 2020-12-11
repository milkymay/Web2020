<template>
    <div>
        <Post :post="post" :comments="comments" :users="users" :showComments="true"/>
    </div>
</template>

<script>
    import Post from "@/components/middle/Post";

    export default {
        name: "Index",
        components: {
            Post
        },
        props: ["post", "users", "comments"],
    }
</script>

<style scoped>

</style>