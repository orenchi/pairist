<script>
    export let member;
</script>

<style>
    .pair-card {
        border: 1px solid #eee;
        border-radius: 4px;
        box-shadow: 2px 2px 4px #999999;
        min-height: 2em;
        min-width: 200px;
        max-width: 200px;
        padding: 0.5em 0.5em 0.5em 5em;
        flex: auto;
    }
</style>
<div class="pair-card">
    <b>Name: </b>{member.fName + " " + member.lName}<br>
</div>