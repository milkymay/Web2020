<template>
    <div>
        <a style="font-style: italic">{{ users[comment.userId].name }}:</a> {{ comment.text }}
    </div>
</template>

<script>
    export default {
        name: "Comment",
        props: ["comment", "users"]
    }
</script>

<style scoped>

</style>