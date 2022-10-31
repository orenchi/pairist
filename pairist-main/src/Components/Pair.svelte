<script>
    export let pairsList;
    import PairCard from "./PairCard.svelte";

</script>

<style>
    .pair-list {
        display: flex;
        flex-wrap: wrap;
    }
</style>

<div id="pairList">

    <h3 aria-label="Pair List">Pair List</h3>
        <div class="pair-list">
        {#each pairsList as pair}
            <PairCard pair={pair}></PairCard>
        {/each}
        </div>
</div>