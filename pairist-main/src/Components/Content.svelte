<script>
    import  { getContext } from 'svelte';
    import { fly } from 'svelte/transition';

    import Popup from './Popup.svelte';
    import PopupLong from './PopupLong.svelte';
    import Dialog from './Dialog.svelte';
    import CloseButton from './CloseButton.svelte';


    const { open } = getContext('simple-modal');

    let opening = false;
    let opened = false;
    let closing = false;
    let closed = false;

    export let pair;

    const showPopup = () => {
        open(Popup, { pair: pair });
    };


    let title;
    let description;
    let status = 0;

    const onCancel = (text) => {
        title = '';
        status = -1;
    }

    const onOkay = (newTitle, newDescription) => {
        title = newTitle;
        description = newDescription;
        status = 1;

        //build json body
        var newTask = `{
                       "title": "${title}",
                       "description": "${description}"
                       }`;
        //post request to localhost:8080/assignTask
        fetch(`http://localhost:8080/assignTask?pairId=${pair.id}`,{
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            method: 'PUT',
            body: newTask,
        }).then(async function (response) {
                if(response.status == 200){
                    let task = await response.json();
                    pair = task;

                } else {
                    alert("Error assigning task");
                }
            });
    }

    const showDialog = () => {
        open(
            Dialog,
            {
                message: "Title",
                hasForm: true,
                onCancel,
                onOkay
            },
            {
                closeButton: false,
                closeOnEsc: false,
                closeOnOuterClick: false,
            }
        );
    };
</script>

<section>
    <br/>
    <button on:click={showPopup}>View Pair info</button>
    <button on:click={showDialog}>Assign New Task</button>

    {#if status === 1}
        <p>New task: {title} assigned to pair</p>
    <!--{:else if status === -1}-->
    <!--    <p><em>Dialog was canceled</em></p>-->
    {/if}

    <div id="state">
        {#if opening}
            <p>opening modal...</p>
        {:else if opened}
            <p>opened modal!</p>
        {:else if closing}
            <p>closing modal...</p>
        {:else if closed}
            <p>closed modal!</p>
        {/if}
    </div>
</section>

<style>
    section {
        padding-top: 0.5em;
    }

    #state {
        position: absolute;
        top: 0;
        right: 0;
        opacity: 0.33;
        font-size: 0.8em;
    }
</style>