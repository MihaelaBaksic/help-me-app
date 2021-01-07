import React from "react";
import { List, Button, Label, Grid } from "semantic-ui-react";
import { useEffect, useState } from "react";
import { useHistory, withRouter } from "react-router-dom";

//const baseUrl = `${process.env.PUBLIC_URL}`;
const baseUrl = "http://localhost:8080";
function RequestList(props) {
	let history = useHistory();
	const [requests, setRequests] = useState("");

	function pogledajUsera(username) {
		/* console.log(username); */
		history.push("/user/" + username);
	}
	function pregledajZahtjev(valerija) {
		/* console.log(valerija); */
		history.push("/request/" + valerija);
	}

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
			setRequests(props.listaZahtjeva);
		} else if (props.username) {
			fetch(baseUrl + "/user/authoredRequests/" + props.username, options)
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
					/* console.log("error: ", error, "LISTA VJEROJATNO PRAZNA"); */
					setRequests("");
				});
		}
	}, [props.listaZahtjeva, props.username]);
	if (requests) {
		return (
			<List selection celled id="requestList" className="centerContent">
				{/* console.log(requests) */}
				{requests.map((request) => (
					<List.Item key={request.id}>
						<Grid stackable padded style={{ cursor: "initial" }}>
							<Grid.Row columns={2}>
								<Grid.Column>
									<List.Header>
										<Label
											as="a"
											image
											onClick={(e) =>
												pogledajUsera(
													request.requestAuthor
														.username,
													e
												)
											}
										>
											<img
												src="https://react.semantic-ui.com/images/avatar/small/joe.jpg"
												alt=""
											/>
											&nbsp;
											{request.requestAuthor.username}
											{request.address ? (
												<Label.Detail>
													{
														request.address
															.description
													}
												</Label.Detail>
											) : (
												<Label.Detail>
													Virtualni zahtjev
												</Label.Detail>
											)}
										</Label>

										<div
											id="requestTitle"
											onClick={(e) =>
												pregledajZahtjev(request.id, e)
											}
										>
											{request.title}
										</div>
									</List.Header>
								</Grid.Column>
								<Grid.Column
									floated="right"
									onClick={(e) =>
										pregledajZahtjev(request.id, e)
									}
								>
									<Button color="blue" floated="right">
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
			<div
				selection
				celled
				id="requestList"
				className="card centerContent"
			>
				<h1>Lista zahtjeva je prazna</h1>
			</div>
		);
	}
}

export default withRouter(RequestList);
