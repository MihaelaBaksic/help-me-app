import React from "react";
import { List, Button, Label, Divider } from "semantic-ui-react";
import { useEffect, useState, useCallback } from "react";
import FilterComponent from "./FilterComponent";

//const baseUrl = `${process.env.PUBLIC_URL}`;
const baseUrl = "http://localhost:8080";
function FilterRequestList(props) {
	let filter = {
		radius: 5,
		virtual: false,
		order: "STANDARD",
	};
	const [requestsF, setRequestsF] = useState("");
	const [filterBody, setFilterBody] = useState(JSON.stringify(filter));

	useEffect(() => {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		myHeaders.append("Content-Type", "application/json");
		console.log("PA FAMILIJO STA JE BELAJ");

		const options = {
			method: "POST",
			headers: myHeaders,
			redirect: "follow",
			body: filterBody,
		};
		if (props.listaZahtjeva) {
			setRequestsF(props.listaZahtjeva._embedded.requestDTOList);
		} else {
			fetch(
				props.username
					? baseUrl + "/user/authoredRequests/" + props.username
					: baseUrl + "/requests/all",
				options
			)
				.then((response) => response.text())
				.then((result) => {
					if (JSON.parse(result)._embedded) {
						setRequestsF(
							JSON.parse(result)._embedded.requestDTOList
						);
					} else {
						setRequestsF("");
					}
				})
				.catch((error) => {
					console.log("error: ", error, "LISTA VJEROJATNO PRAZNA");
					setRequestsF("");
				});
		}
	}, [filterBody]);

	if (requestsF) {
		console.log("Body = ");
		console.log(filterBody);
		return (
			<List selection celled id="requestList">
				<FilterComponent
					setFilterBody={(body) => setFilterBody(body)}
				/>
				<Divider hidden fitted />
				{requestsF.map((request) => (
					<List.Item key={request.id}>
						<List.Header>
							{request.title}
							<Label as="a" image>
								<img
									src="https://react.semantic-ui.com/images/avatar/small/joe.jpg"
									alt=""
								/>
								{request.requestAuthor.username}
							</Label>
						</List.Header>
						{request.description}
						{request.address ? (
							<Label tag>{request.address.description}</Label>
						) : (
							<Label tag>Virtualni zahtjev</Label>
						)}

						<Button positive floated="right">
							Javi se
						</Button>
					</List.Item>
				))}
			</List>
		);
	} else {
		return (
			<div>
				<FilterComponent
					setFilterBody={(body) => setFilterBody(body)}
				/>
				<h1>Ne postoje takvi zahtjevi!</h1>
			</div>
		);
	}
}

export default FilterRequestList;
