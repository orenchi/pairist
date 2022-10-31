<script>
    import Modal from "./Modal.svelte";
    import Content from "./Content.svelte";

    export let pair;
    let modal;
</script>

<style>
    .pair-card {
        border: 1px solid #eee;
        border-radius: 4px;
        box-shadow: 2px 2px 4px #999999;
        min-height: 5em;
        min-width: 419px;
        max-width: 419px;
        padding: 0.5em 0.5em 0.5em 5em;
        flex: auto;
    }
</style>
<div class="pair-card">
    <b>Name: </b>{pair.members[0].fName}<br>
    <b>Name: </b>{pair.members[1].fName}

    <Modal show={$modal} >
        <Content pair={pair} />
    </Modal>
</div>