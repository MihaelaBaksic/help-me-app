import React from "react";
import {
	Card,
	Icon,
	List,
	Button,
	Label,
	Grid,
	Image,
} from "semantic-ui-react";
import { useEffect, useState } from "react";
import OtherUserProfile from "./OtherUserProfile";

const baseUrl = "http://localhost:8080";

function Statistics() {
	const [topUsers, setTopUsers] = useState("");

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

		fetch(baseUrl + "/rating/statistics", options)
			.then((response) => response.text())
			.then((result) =>
				setTopUsers(JSON.parse(result)._embedded.userDTOList)
			);
		console.log(JSON.stringify(topUsers));
	}, []);

	if (topUsers) {
		return (
			<Grid id="grid" centered columns={3}>
				<Grid.Column>
					<Card>
						<Image
							src="https://react.semantic-ui.com/images/avatar/large/matthew.png"
							wrapped
							ui={false}
						/>
						<Card.Content>
							<Card.Header>{topUsers[0].username}</Card.Header>
							<Card.Meta>
								<span className="date">Najbolji pomagač!</span>
							</Card.Meta>
						</Card.Content>
					</Card>
				</Grid.Column>

				<Grid.Row centered columns={4}>
					<Grid.Column>
						<Card>
							<Image
								src="https://react.semantic-ui.com/images/avatar/large/elliot.jpg"
								wrapped
								ui={false}
							/>
							<Card.Content>
								<Card.Header>
									{topUsers[1].username}
								</Card.Header>
								<Card.Meta>
									<span className="date">
										Skoro najbolji pomagač!
									</span>
								</Card.Meta>
							</Card.Content>
						</Card>
					</Grid.Column>
					<Grid.Column>
						<Card>
							<Image
								src="https://react.semantic-ui.com/images/avatar/large/daniel.jpg"
								wrapped
								ui={false}
							/>
							<Card.Content>
								<Card.Header>
									{topUsers[2].username}
								</Card.Header>
								<Card.Meta>
									<span className="date">
										Jako koristan pomagač!
									</span>
								</Card.Meta>
							</Card.Content>
						</Card>
					</Grid.Column>
				</Grid.Row>
			</Grid>
		);
	} else {
		return null;
	}
}

export default Statistics;
