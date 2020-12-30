import React from "react";
import { List, Button, Label, Divider, Grid } from "semantic-ui-react";
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
					<List.Item key={request.id} /*onClick={}*/>
						<Grid padded>		
							<Grid.Row columns={2}>
								<Grid.Column>
									<List.Header>
										<Label as="a" image>
											<img
												src="https://react.semantic-ui.com/images/avatar/small/joe.jpg"
												alt=""
											/>
											&nbsp;
											{request.requestAuthor.username}
											{request.address ? <Label.Detail>{request.address.description}</Label.Detail>
										: <Label.Detail>Virtualni zahtjev</Label.Detail>}
										</Label>
										<br/>
										<div id="requestTitle" >{request.title}</div>
									</List.Header>
								</Grid.Column>	
								<Grid.Column floated="right">
									<Button color='blue' floated="right">
										Pregledaj
									</Button>
								</Grid.Column>
							</Grid.Row>
						</Grid>		
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
