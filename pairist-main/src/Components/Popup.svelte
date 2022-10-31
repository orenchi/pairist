<script>
    export let message = 'Pair Info';
    export let pair;
    export let pair_var;

    pair_var = pair;

</script>

<style>
    h2 {
        font-size: 2rem;
        text-align: center;
    }
</style>

{#if pair !== undefined}

    <h2> {message} 👥</h2>
    <!--<h2> Members: {pair.members[0]} </h2>-->

    <h3> Members:</h3>

    {#key pair_var}
        <p> {pair.members[0].fName} {pair.members[0].lName}</p>
        <p> {pair.members[1].fName} {pair.members[1].lName}</p>
        <br/>

        <h3> Current Task:</h3>
        {#if pair_var.currentTask !== null }
            <p> {pair_var.currentTask.title}</p>
            <p> {pair_var.currentTask.description}</p>
            {:else }
            <p>No task assigned!</p>
        {/if}
    {/key}
{/if}