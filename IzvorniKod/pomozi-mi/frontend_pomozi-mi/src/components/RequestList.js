import React from "react";
import { List, Button, Label } from "semantic-ui-react";
import { useEffect, useState } from "react";

const baseUrl = `${process.env.PUBLIC_URL}`;

function RequestList() {
	const [requests, setRequests] = useState([]);

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

		fetch(baseUrl + "/requests", options)
			.then((response) => response.text())
			.then((result) =>
				setRequests(JSON.parse(result)._embedded.requestDTOList)
			)
			.catch((error) => console.log("error", error));
	}, []);

	return (
		<List selection celled id="requestList">
			{requests.map((request) => (
				<List.Item>
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
					<Label as="a" tag>
						{JSON.stringify(request.address)}
					</Label>
					<Button positive floated="right">
						Javi se
					</Button>
				</List.Item>
			))}
		</List>
	);
}

export default RequestList;
