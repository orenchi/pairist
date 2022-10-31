<script context="module">

    export function load({ params }) {
        const { teamName } = params
        return { props: { teamName } }
    }
</script>

<script>
    import Member from "../../Components/Member.svelte";
    import Pair from "../../Components/Pair.svelte";
    import {onMount} from 'svelte';

    export let teamName;
    export let input_password = "";
    export let first_name = "";
    export let last_name = "";
    export let team_id;
    export let team_memberlist;
    export let team_pairlist;
    export let exists;

    let memberList1 = "memberList1"
    let memberList2 = "memberList2"
    let selectMember1Text = "Select member 1";
    let selectMember2Text = "Select member 2";
    let login_id = "login";
    let teampage_id = "teampage";
    let team;
    let logged_in;
    let member;
    let selected1;
    let selected2;
    let pairs

    onMount( () => {
        checkIfLoggedIn()
    })

    async function checkIfLoggedIn() {
        logged_in = document.cookie.includes(`loggedIn=${teamName}`)
        if (logged_in) {
            await get_team()
        }
    }

    function enterPassword(pword) {
        fetch(`http://localhost:8080/verifyLogIn?teamName=${teamName}&pword=${pword}`, {method: "POST", headers: {
                'Content-Type': 'application/json;charset=utf-8'}})
            .then(async function (response) {
                if (response.status == 401) {
                    alert("Incorrect Password!");
                    throw 401;
                } else if (response.status == 200) {
                    team = await response.json();
                    setTeamInformation(team)
                    logged_in = true
                    document.cookie = `loggedIn=${teamName}`
                }
                else {
                    alert("Something went wrong!");
                }
            })
    }

    function addMember(first, last) {
        fetch(`http://localhost:8080/addMember?fName=${first}&lName=${last}&teamId=${team_id}`, {method: "POST", headers: {
                'Content-Type': 'application/json;charset=utf-8'}})
            .then(async function (response) {
            if (response.status == 200) {
                member = await response.json();
                team.memberList.push(member);
                team_memberlist = team.memberList
            }else {
                alert("Missing fields!");
            }
        })
    }

    async function  createPair() {
        fetch(`http://localhost:8080/addPair?member1Id=${selected1.id}&member2Id=${selected2.id}&teamId=${team_id}`, {method: "POST", headers: {
                'Content-Type': 'application/json;charset=utf-8'}})
            .then(async function (response) {
            if(response.status == 200){
                let pair = await response.json();
                team.pairs.push(pair);
                team_pairlist = team.pairs;

                let memberList1_element = document.getElementById(memberList1)
                let memberList2_element = document.getElementById(memberList2)

                let disabled1_element = document.createElement("option")
                disabled1_element.disabled = true
                disabled1_element.selected = true
                disabled1_element.hidden = true
                disabled1_element.innerHTML = selectMember1Text

                let disabled2_element = document.createElement("option")
                disabled2_element.disabled = true
                disabled2_element.hidden = true
                disabled2_element.selected = true
                disabled2_element.innerHTML = selectMember2Text

                memberList1_element.appendChild(disabled1_element)
                memberList2_element.appendChild(disabled2_element)
                }
            });
    }

    function isDisabled_dropdown1(member){
        return member == selected2;
    }

    function isDisabled_dropdown2(member){
        return member == selected1;
    }

    function pairAlreadyExists() {
        let bruh = false;
        if (selected1 && selected2) {
            team_pairlist.forEach(pair => {
                if (((pair.members[0].id == selected1.id) && (pair.members[1].id == selected2.id)) || ((pair.members[0].id == selected2.id) && (pair.members[1].id == selected1.id))) {
                    bruh = true;
                }
            });

            return bruh
        }
    }

    function shuffle() {
        fetch(`http://localhost:8080/randomPair?teamId=${team_id}`, {method: "PUT", headers: {
                'Content-Type': 'application/json;charset=utf-8'}})
            .then(async function (response) {
                if (response.status == 200) {
                    pairs = await response.json();
                    team.pairs = pairs
                    team_pairlist = pairs
                }
            })
    }

    function setTeamInformation(team){
        team_id = team.id;
        team_memberlist = team.memberList
        team_pairlist = team.pairs
    }

    function get_team() {
        fetch(`http://localhost:8080/getTeam?teamName=${teamName}`, {
            method: "GET", headers: {
                'Content-Type': 'application/json;charset=utf-8'
            }
        })
            .then(async function (response) {
                if (response.status == 401) {
                    alert("Incorrect Password!");
                    throw 401;
                } else if (response.status == 200) {

                    team = await response.json();
                    setTeamInformation(team)
                } else {
                    alert("Something went wrong!");
                }
            })
    }
</script>

<main>
    {#if logged_in !== undefined && logged_in == false}
    {#key logged_in}
            <div id ={login_id}>
                <div id="container">
                    <h1>{"Team: " + teamName}</h1>
                    <form on:submit|preventDefault={() => enterPassword(input_password)}>
                        <label>
                            Enter team password:
                            <input bind:value={input_password} type="password">
                        </label>

                        <button >Submit </button>
                    </form>
                </div>
            </div>
    {/key}
    {/if}

    <div id={teampage_id}>
        <br>
        {#if team !== undefined}
            <h1>Welcome, {teamName}!</h1>
            <div class="container">
                <h1>{"Add new member"}</h1>
                <input bind:value={first_name} placeholder="Enter first name"/> <input bind:value={last_name} placeholder="Enter last name"/>
                <button on:click={addMember(first_name, last_name, team_id)}>Submit</button>
            </div>
            {#key team_memberlist}
            <Member membersList={team_memberlist}></Member>
            {/key}

            <br>

            <div class="container">
                <h1>{"Manually assign pair"}</h1>
                <form on:submit|preventDefault={createPair}  on:change="{() => exists = pairAlreadyExists()}">
                    <select bind:value={selected1}  id="memberList1">
                        <option value="" disabled selected hidden>Select member 1</option>

                        {#each team_memberlist as member}
                            <option disabled={isDisabled_dropdown1(member)} value={member}>
                                {member.fName + " " + member.lName}
                            </option>
                        {/each}

                    </select>
                    <select bind:value={selected2} id="memberList2">
                        <option value="" disabled selected hidden>Select member 2</option>

                        {#each team_memberlist as member}
                            <option disabled={isDisabled_dropdown2(member)} value={member}>
                                {member.fName + " " + member.lName}
                            </option>
                        {/each}

                    </select>

                    {#key exists}
                        <br>
                        <button disabled={exists || !selected1 || !selected2} type=submit>
                            Create Pair
                        </button> <button on:click={shuffle}>Shuffle Pairs</button>
                        {#if exists}
                            <p style="color:darkred; font-weight:bold; font-size:24px"> This pair already exists!</p>
                        {/if}
                    {/key}
                </form>
            </div>
            {#key team_pairlist}
            <Pair pairsList="{team_pairlist}"></Pair>
                {/key}

        {/if}



    </div>

</main>