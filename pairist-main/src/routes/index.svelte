

<script>
		import { onMount } from 'svelte';
		export let title_text = "Pairist";
		let team_text = "Teams:";
		let team_list_id = "team_list";

		
		onMount(() => {
			getAllTeams();
		})

	function addNewTeam(text,  password) {
		fetch(`http://localhost:8080/addTeam?name=${text}&pword=${password}`, {method: "POST", headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}}).then(function (response) {
			if (response.status == 400) {
				alert("No team name or password!");

			}
			else if (response.status == 500) {alert("Duplicate team!")}
			else {
					return response.json();
				}

		}).then(function (data) {
			createTeamList(data);
		})

	}

		function createTeamList(team) {
			let teams_list_element = document.getElementById(team_list_id)
			let new_team = document.createElement("li");
			let new_team_link = document.createElement("a");
			new_team_link.textContent = team.name;
			new_team.appendChild(new_team_link);
			new_team_link.setAttribute("href", `team/${new_team_link.textContent}`);
			teams_list_element.appendChild(new_team);
		}

		//localhost:8000/team?name=teamName
	function getAllTeams() {

		fetch(`http://localhost:8080/getAllTeams`, {method: "GET", headers: {
				'Content-Type': 'application/json;charset=utf-8'
			}}).then(function (response) {
				if (response.status == 400) {
					throw "400";
				}
				return response.json();
		}).then(function (data) {
			console.log(data);
			data.forEach(team => {
				createTeamList(team);
			})
		})
	}

	export let input_name = "";
	export let input_password = "";


</script>


<main>
	<div class="container">
		<h1>{title_text}</h1>
		<br>
		<div>
			<input bind:value={input_name} placeholder="Enter new team name">
			<br>
			<input bind:value={input_password} type="password"  placeholder="Enter new password"  />
		</div>
		<br>
		<button on:click={addNewTeam(input_name, input_password)}>Add team</button>
		<br>
		<div>
			<p>{team_text}</p>
			<br>
			<div id="teamlistdiv">
			<ul id={team_list_id}></ul>
			</div>
		</div>
	</div>
</main>


<style>
	main {
		text-align: left;
		padding: 1em;
		max-width: 240px;
		margin: 0 auto;

	}

	h1 {
		color: #fffb00;
		text-transform: uppercase;
		font-size: 4em;
		font-weight: 100;
		margin-left: 1px;
	}

	@media (min-width: 640px) {
		main {
			max-width: none;
		}
	}
</style>