<template>
    <div class="commentSection" style="margin-left: 2rem">
        <div class="header" style="font-weight: bold; color: cornflowerblue">Comment section</div>
        <Comment v-for="comment in postComments" :comment="comment" :users="users" :key="comment.id"/>
    </div>
</template>

<script>
    import Comment from "@/components/middle/Comment";

    export default {
        name: "Comments",
        components: {
            Comment
        },
        props: ["postComments", "users"]
    }
</script>

<style scoped>

</style>