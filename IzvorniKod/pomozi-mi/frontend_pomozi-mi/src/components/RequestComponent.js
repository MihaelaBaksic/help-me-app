import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import {
	Label,
	Container,
	Button,
	Card,
	Icon,
	CardContent,
	Header,
} from "semantic-ui-react";
import PotentialUsers from "./PotentialUsers";
import L from "leaflet";
import { MapContainer, TileLayer, Marker } from "react-leaflet";

//const baseUrl = `${process.env.PUBLIC_URL}`;
const baseUrl = "http://localhost:8080";

function RequestComponent(props) {
	const { id } = useParams();
	const [podaciReq, setPodaciReq] = useState([]);
	const [podaciUser, setPodaciUser] = useState([]);

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

		fetch(baseUrl + `/requests/${id}`, options)
			.then((response) => response.text())
			.then((result) => setPodaciReq(JSON.parse(result)))
			.catch((error) => console.log("error", error));
	}, [id]);

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
	}, [id]);

	async function blockRequest() {
		var myHeaders = new Headers();

		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);

		const options = {
			method: "POST",
			headers: myHeaders,
		};
		await fetch(
			baseUrl + `/requests/blockDeleteRequest/${id}`,
			options
		).then((response) => {
			if (response.status === 200) {
				console.log("Uspješano blokiranje");
				window.location.reload(false);
			} else {
				console.log("Neuspješano blokiranje");
			}
		});
	}

	async function markAsDone() {
		var myHeaders = new Headers();
		var urlEncoded = new URLSearchParams();

		urlEncoded.append("username", podaciReq.handler.username);
		urlEncoded.append("firstName", podaciReq.handler.password);
		urlEncoded.append("lastName", podaciReq.handler.lastName);
		urlEncoded.append("email", podaciReq.handler.email);
		urlEncoded.append("administrator", podaciReq.handler.administrator);
		urlEncoded.append("status", podaciReq.handler.status);

		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);

		const options = {
			method: "POST",
			headers: myHeaders,
		};
		await fetch(baseUrl + `/requests/markDone/${id}`, options).then(
			(response) => {
				if (response.status === 200) {
					console.log("Uspješano obavljanje");
					window.location.reload(false);
				} else {
					console.log("Neuspješano obavljanje");
				}
			}
		);
	}

	async function takeRequest() {
		var myHeaders = new Headers();

		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);

		const options = {
			method: "POST",
			headers: myHeaders,
		};
		await fetch(baseUrl + `/requests/respond/${id}`, options).then(
			(response) => {
				if (response.status === 200) {
					console.log("Uspješano javljanje");
				} else {
					console.log("Neuspješano javljanje");
				}
			}
		);
	}

	console.log(JSON.stringify(podaciReq));
	console.log(JSON.stringify(podaciUser));

	if (podaciReq.address !== undefined) {
		let moj = "Ne";
		let btnL = "Javi se";
		let btnR = "";
		if (podaciUser.administrator === true) {
			moj = "Da";
			btnL = "Javi se";
			btnR = "Ukloni zahtjev";
		}
		if (podaciReq.requestAuthor.username === podaciUser.username) {
			moj = "Da";
			btnL = "Pregled javljanja";
			btnR = "Blokiraj";
		}

		let buttonLeft = (
			<Button
				onClick={() => takeRequest()}
				color="blue"
				size="large"
				floated="right"
			>
				{btnL}
			</Button>
		);
		let buttonRight = (
			<Button
				onClick={() => blockRequest()}
				color="red"
				size="large"
				floated="right"
			>
				{btnR}
			</Button>
		);

		//Ako je zahtjev blokiran
		if (podaciReq.status === "BLOCKED") {
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
								Krajnji datum = {podaciReq.expirationDate}
							</Label>
							{podaciReq.address ? (
								<Label color="orange">
									Adresa: {podaciReq.address.description}
								</Label>
							) : (
								<Label color="orange">Virtualni zahtjev</Label>
							)}
							<Label color="orange">Autor Zahtjeva = {moj}</Label>
						</Card.Content>
						<Card.Content
							header={podaciReq.title}
							description={podaciReq.description}
						/>
						<Card.Content extra>
							<div>
								<h2 color="red"> Zahtjev je blokiran! </h2>
							</div>
						</Card.Content>
					</Card>
				</Container>
			);
		}

		//Ako je zahtjev gotov
		if (podaciReq.status === "DONE") {
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
								Krajnji datum = {podaciReq.expirationDate}
							</Label>
							{podaciReq.address ? (
								<Label color="orange">
									Adresa: {podaciReq.address.description}
								</Label>
							) : (
								<Label color="orange">Virtualni zahtjev</Label>
							)}
							<Label color="orange">Autor Zahtjeva = {moj}</Label>
						</Card.Content>
						<Card.Content
							header={podaciReq.title}
							description={podaciReq.description}
						/>
						<Card.Content extra>
							<div>
								<h2 color="red">
									{" "}
									Zahtjev je izvršio{" "}
									{podaciReq.handler.username}{" "}
								</h2>
							</div>
						</Card.Content>
					</Card>
				</Container>
			);
		}

		//Prednost ako je autor
		if (
			podaciReq.requestAuthor.username === podaciUser.username &&
			podaciReq.status === "ACTANS"
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
								Krajnji datum = {podaciReq.expirationDate}
							</Label>
							{podaciReq.address ? (
								<Label color="orange">
									Adresa: {podaciReq.address.description}
								</Label>
							) : (
								<Label color="orange">Virtualni zahtjev</Label>
							)}
							<Label color="orange">Autor Zahtjeva = {moj}</Label>
						</Card.Content>
						<Card.Content
							header={podaciReq.title}
							description={podaciReq.description}
						/>
						<Card.Content extra>
							<div>
								{buttonRight}
								<PotentialUsers id={id}></PotentialUsers>
							</div>
						</Card.Content>
						{podaciReq.address ? (
							<Card.Content>
								<MapContainer
									center={L.latLng(
										podaciReq.address.x_coord,
										podaciReq.address.y_coord
									)}
									zoom={13}
									scrollWheelZoom={true}
									className="form-group"
								>
									<TileLayer
										attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors, <a href="https://github.com/hrvoje459" target="_blank">hrvoje459</a> '
										url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
									/>
									<Marker
										draggable={false}
										position={L.latLng(
											podaciReq.address.x_coord,
											podaciReq.address.y_coord
										)}
									></Marker>
								</MapContainer>
							</Card.Content>
						) : null}
					</Card>
				</Container>
			);
		} else if (
			podaciReq.requestAuthor.username === podaciUser.username &&
			podaciReq.status === "ACTNOANS"
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
								Krajnji datum = {podaciReq.expirationDate}
							</Label>
							{podaciReq.address ? (
								<Label color="orange">
									Adresa: {podaciReq.address.description}
								</Label>
							) : (
								<Label color="orange">Virtualni zahtjev</Label>
							)}
							<Label color="orange">Autor Zahtjeva = {moj}</Label>
						</Card.Content>
						<Card.Content
							header={podaciReq.title}
							description={podaciReq.description}
						/>
						<Card.Content extra>
							<div>{buttonRight}</div>
						</Card.Content>
						{podaciReq.address ? (
							<Card.Content>
								<MapContainer
									center={L.latLng(
										podaciReq.address.x_coord,
										podaciReq.address.y_coord
									)}
									zoom={13}
									scrollWheelZoom={true}
									className="form-group"
								>
									<TileLayer
										attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors, <a href="https://github.com/hrvoje459" target="_blank">hrvoje459</a> '
										url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
									/>
									<Marker
										draggable={false}
										position={L.latLng(
											podaciReq.address.x_coord,
											podaciReq.address.y_coord
										)}
									></Marker>
								</MapContainer>
							</Card.Content>
						) : null}
					</Card>
				</Container>
			);
		} else if (
			podaciReq.requestAuthor.username === podaciUser.username &&
			podaciReq.status === "ACCEPTED"
		) {
			btnL = "Zahtjev završen";
			buttonLeft = (
				<Button
					onClick={() => markAsDone()}
					color="blue"
					size="large"
					floated="right"
				>
					{btnL}
				</Button>
			);
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
								Krajnji datum = {podaciReq.expirationDate}
							</Label>
							{podaciReq.address ? (
								<Label color="orange">
									Adresa: {podaciReq.address.description}
								</Label>
							) : (
								<Label color="orange">Virtualni zahtjev</Label>
							)}
							<Label color="orange">Autor Zahtjeva = {moj}</Label>
						</Card.Content>
						<Card.Content
							header={podaciReq.title}
							description={podaciReq.description}
						/>
						<Card.Content extra>
							<div>
								{buttonRight}
								{buttonLeft}
							</div>
						</Card.Content>
						{podaciReq.address ? (
							<Card.Content>
								<MapContainer
									center={L.latLng(
										podaciReq.address.x_coord,
										podaciReq.address.y_coord
									)}
									zoom={13}
									scrollWheelZoom={true}
									className="form-group"
								>
									<TileLayer
										attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors, <a href="https://github.com/hrvoje459" target="_blank">hrvoje459</a> '
										url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
									/>
									<Marker
										draggable={false}
										position={L.latLng(
											podaciReq.address.x_coord,
											podaciReq.address.y_coord
										)}
									></Marker>
								</MapContainer>
							</Card.Content>
						) : null}
					</Card>
				</Container>
			);
		} else if (podaciReq.requestAuthor.administrator === true) {
			if (podaciReq.status === "ACCEPTED") {
				buttonLeft = (
					<Button disabled color="blue" size="large" floated="right">
						{btnL}
					</Button>
				);
			}
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
								Krajnji datum = {podaciReq.expirationDate}
							</Label>
							{podaciReq.address ? (
								<Label color="orange">
									Adresa: {podaciReq.address.description}
								</Label>
							) : (
								<Label color="orange">Virtualni zahtjev</Label>
							)}
							<Label color="orange">Autor Zahtjeva = {moj}</Label>
						</Card.Content>
						<Card.Content
							header={podaciReq.title}
							description={podaciReq.description}
						/>
						<Card.Content extra>
							<div>
							{podaciReq.handler === null ? "" : (podaciReq.handler.username === podaciUser.username ? <Header size='large' color='green'>Autor Vas je izabrao kao izvršitelja!</Header> : <Header size='large' color='red'>Autor Vas NIJE izabrao kao izvršitelja!</Header>)}
								{buttonRight}
								{buttonLeft}
							</div>
						</Card.Content>
						{podaciReq.address ? (
							<Card.Content>
								<MapContainer
									center={L.latLng(
										podaciReq.address.x_coord,
										podaciReq.address.y_coord
									)}
									zoom={13}
									scrollWheelZoom={true}
									className="form-group"
								>
									<TileLayer
										attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors, <a href="https://github.com/hrvoje459" target="_blank">hrvoje459</a> '
										url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
									/>
									<Marker
										draggable={false}
										position={L.latLng(
											podaciReq.address.x_coord,
											podaciReq.address.y_coord
										)}
									></Marker>
								</MapContainer>
							</Card.Content>
						) : null}
					</Card>
				</Container>
			);
		} else {
			if (podaciReq.status === "ACCEPTED") {
				buttonLeft = (
					<Button disabled color="blue" size="large" floated="right">
						{btnL}
					</Button>
				);
			}
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
								Krajnji datum = {podaciReq.expirationDate}
							</Label>
							{podaciReq.address ? (
								<Label color="orange">
									Adresa: {podaciReq.address.description}
								</Label>
							) : (
								<Label color="orange">Virtualni zahtjev</Label>
							)}
							<Label color="orange">Autor Zahtjeva = {moj}</Label>
						</Card.Content>
						<Card.Content
							header={podaciReq.title}
							description={podaciReq.description}
						/>
						<Card.Content extra>
							<div>
							{podaciReq.handler === null ? "" : (podaciReq.handler.username === podaciUser.username ? <Header size='large' color='green'>Autor Vas je izabrao kao izvršitelja!</Header> : <Header size='large' color='red'>Autor Vas NIJE izabrao kao izvršitelja!</Header>)}
								{buttonLeft}
							</div>
						</Card.Content>
						{podaciReq.address ? (
							<Card.Content>
								<MapContainer
									center={L.latLng(
										podaciReq.address.x_coord,
										podaciReq.address.y_coord
									)}
									zoom={13}
									scrollWheelZoom={true}
									className="form-group"
								>
									<TileLayer
										attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors, <a href="https://github.com/hrvoje459" target="_blank">hrvoje459</a> '
										url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
									/>
									<Marker
										draggable={false}
										position={L.latLng(
											podaciReq.address.x_coord,
											podaciReq.address.y_coord
										)}
									></Marker>
								</MapContainer>
							</Card.Content>
						) : null}
					</Card>
				</Container>
			);
		}
	} else {
		return null;
	}
}

export default RequestComponent;
