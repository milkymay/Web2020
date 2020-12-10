<template>
    <footer>
        <div> {{ usersCount }} users registered, {{ postsCount }} posts written </div>
        <a href="/">Codeforces</a> &copy; 2020 by Mike Mirzayanov
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["usersCount", "postsCount"],
}
</script>

<style scoped>

</style>