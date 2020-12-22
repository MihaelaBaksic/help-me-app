import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import {
	Label,
	Container,
	Button,
	Card,
	Icon,
	CardContent,
} from "semantic-ui-react";

//const baseUrl = `${process.env.PUBLIC_URL}`;
const baseUrl = "http://localhost:8080";

function RequestComponent(props) {
	//const { id } = useParams();
	const [podaciReq, setPodaciReq] = useState([]);
	const [podaciUser, setPodaciUser] = useState([]);

	let description = [
		"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.",
		"At vero eos et accusam et justo duo dolores et ea rebum.",
		"Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.",
	].join(" ");

	let name = "Mateo Stanić | @mateostanic69  ";
	let vrijeme = "22:00:00";
	let adresaZah = "VIRTUELNI";
	let colorBut1 = "green";
	let colorBut2 = "red";

	//Ovo je za fetch requesta
	useEffect(() => {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		const options = {
			method: "GET",
			headers: myHeaders,
			redirect: "follow",
		};

		fetch(baseUrl + `/requests/2`, options) // `/requests/${id}` Ovo je hardcodirano zbog trenutnih uslova ali treba ici id
			.then((response) => response.text())
			.then((result) => setPodaciReq(JSON.parse(result)))
			.catch((error) => console.log("error", error));
	}, []);

	//Ovo je trenutno redudantno ali se hvata trenutni user
	useEffect(() => {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		const options = {
			method: "GET",
			headers: myHeaders,
			redirect: "follow",
		};

		fetch(baseUrl + "/user/getCurrentUser", options)
			.then((response) => response.text())
			.then((result) => setPodaciUser(JSON.parse(result)))
			.catch((error) => console.log("error", error));
	}, []);

	console.log(JSON.stringify(podaciReq));
	console.log(JSON.stringify(podaciUser));

	if (podaciReq.address !== undefined) {
		let moj = "Ne";
		let btnL = "Javi se";
		let btnR = "";
		if (podaciUser.administrator != false) {
			moj = "Da";
			btnL = "Javi";
			btnR = "Ukloni zahtjev";
		}
		if (podaciReq.requestAuthor.username == podaciUser.username) {
			moj = "Da";
			btnL = "Pregled";
			btnR = "Blokiraj";
		}

		if (
			podaciUser.administrator != false ||
			podaciReq.requestAuthor.username == podaciUser.username
		) {
			return (
				<Container textAlign="justified" color="blue">
					<Card color="red" fluid>
						<Card.Content extra>
							<Icon name="user" size="big" />@
							{podaciReq.requestAuthor.username}
							{" | "}
							{podaciReq.requestAuthor.firstName}{" "}
							{podaciReq.requestAuthor.lastName}{" "}
							<Label color="orange">
								Kranji datum = {podaciReq.expirationDate}
							</Label>
							<Label color="orange">
								Adresa = {podaciReq.address.streetName}{" "}
								{podaciReq.address.streetNumber}
							</Label>
							<Label color="orange">
								Autora Zahtjeva = {moj}
							</Label>
						</Card.Content>
						<Card.Content
							header={podaciReq.title}
							description={podaciReq.description}
						/>
						<Card.Content extra>
							<div>
								<Button
									color={colorBut2}
									size="large"
									floated="right"
								>
									{btnR}
								</Button>
								<Button
									color={colorBut1}
									size="large"
									floated="right"
								>
									{btnL}
								</Button>
							</div>
						</Card.Content>
					</Card>
				</Container>
			);
		} else {
			return (
				<Container textAlign="justified" color="blue">
					<Card color="red" fluid>
						<Card.Content extra>
							<Icon name="user" size="big" />@
							{podaciReq.requestAuthor.username}
							{" | "}
							{podaciReq.requestAuthor.firstName}{" "}
							{podaciReq.requestAuthor.lastName}{" "}
							<Label color="orange">
								Kranji datum = {podaciReq.expirationDate}
							</Label>
							<Label color="orange">
								Adresa = {podaciReq.address.streetName}{" "}
								{podaciReq.address.streetNumber}
							</Label>
							<Label color="orange">
								Autora Zahtjeva = {moj}
							</Label>
						</Card.Content>
						<Card.Content
							header={podaciReq.title}
							description={podaciReq.description}
						/>
						<Card.Content extra>
							<div>
								<Button
									color={colorBut1}
									size="large"
									floated="right"
								>
									{btnL}
								</Button>
							</div>
						</Card.Content>
					</Card>
				</Container>
			);
		}
	} else {
		return null;
	}
}

export default RequestComponent;
