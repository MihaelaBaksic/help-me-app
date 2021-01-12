import React from "react";
import { Card, Grid, Image } from "semantic-ui-react";
import { useEffect, useState } from "react";
import first from "./resources/1st.png";
import second from "./resources/2st.png";
import third from "./resources/3rd.png";

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
		/* console.log(JSON.stringify(topUsers)); */
	}, []);

	if (topUsers) {
		return (
			<Grid
				id="grid"
				centered
				columns={3}
				className="card centerContent"
				style={{ marginTop: 0 + "px" }}
			>
				<Grid.Column style={{ margin: "auto" }}>
					<Card>
						<Image src={first} wrapped ui={false} />
						<Card.Content>
							<Card.Header>{topUsers[0].username}</Card.Header>
							<Card.Meta>
								<span className="date">Najbolji pomagač!</span>
							</Card.Meta>
						</Card.Content>
					</Card>
				</Grid.Column>

				{topUsers[1] ? (
					<Grid.Row centered columns={4}>
						<Grid.Column>
							<Card>
								<Image src={second} wrapped ui={false} />
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
						{topUsers[2] ? (
							<Grid.Column>
								<Card>
									<Image src={third} wrapped ui={false} />
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
						) : null}
					</Grid.Row>
				) : null}
			</Grid>
		);
	} else {
		return null;
	}
}

export default Statistics;
