<script>
    export let membersList;
    import MemberCard from "./MemberCard.svelte";

</script>

<style>
    .member-list {
        display: flex;
        flex-wrap: wrap;
    }
</style>


<div id="memberList">

    <h3 aria-label="Member List">Member List</h3>
    <div class="member-list">
<!--        <ul id="member-ul">-->
<!--            {#key membersList}-->
        {#each membersList as member}
            <MemberCard member={member}></MemberCard>
            {/each}
                <!--{/key}-->
<!--        </ul>-->
    </div>

</div>