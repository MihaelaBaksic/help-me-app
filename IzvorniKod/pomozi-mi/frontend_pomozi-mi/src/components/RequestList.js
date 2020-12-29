import React from "react";
import { List, Button, Label } from "semantic-ui-react";
import { useEffect, useState } from "react";

//const baseUrl = `${process.env.PUBLIC_URL}`;
const baseUrl = "http://localhost:8080";
function RequestList(props) {
	const [requests, setRequests] = useState("");

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
		if (props.listaZahtjeva) {
			setRequests(props.listaZahtjeva._embedded.requestDTOList);
		} else {
			fetch(
				props.username
					? baseUrl + "/user/authoredRequests/" + props.username
					: baseUrl + "/requests",
				options
			)
				.then((response) => response.text())
				.then((result) => {
					if (JSON.parse(result)._embedded) {
						setRequests(
							JSON.parse(result)._embedded.requestDTOList
						);
					} else {
						setRequests("");
					}
				})
				.catch((error) => {
					console.log("error: ", error, "LISTA VJEROJATNO PRAZNA");
					setRequests("");
				});
		}
	}, [props]);
	if (requests) {
		return (
			<List selection celled id="requestList">
				{requests.map((request) => (
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
		return null;
	}
}

export default RequestList;
