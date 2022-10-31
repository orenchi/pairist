<script>
    import { getContext } from 'svelte';
    export let message;
    export let hasForm = false;
    export let onCancel = () => {};
    export let onOkay = () => {};

    const { close } = getContext('simple-modal');

    let value;
    let onChange = () => {};
    let title;
    let description;

    function _onCancel() {
        onCancel();
        close();
    }

    function _onOkay() {
        onOkay(title, description);
        close();
    }

    $: onChange(value)
</script>

<style>
    h2 {
        font-size: 2rem;
        text-align: center;
    }

    input {
        width: 100%;
    }

    .buttons {
        display: flex;
        justify-content: space-between;
    }
</style>

<h2>Title</h2>

{#if hasForm}
    <input
            type="text"
            bind:value={title}
            on:keydown={e => e.which === 13 && _onOkay()} />
{/if}

<h2>Description</h2>

{#if hasForm}
    <input
            type="text"
            bind:value={description}
            on:keydown={e => e.which === 13 && _onOkay()} />
{/if}

<div class="buttons">
    <button on:click={_onCancel}>
        Cancel
    </button>
    <button on:click={_onOkay}>
        Assign task to pair
    </button>
</div>