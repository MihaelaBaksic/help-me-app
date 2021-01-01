import React from "react";
import {
	Message,
	List,
	Button,
	Label,
	Divider,
	Grid,
	Segment,
} from "semantic-ui-react";
import { useEffect, useState, useCallback } from "react";
import { Link } from "react-router-dom";

//const baseUrl = `${process.env.PUBLIC_URL}`;
const baseUrl = "http://localhost:8080";
function Notifications(props) {
	const [notifications, setNotifications] = useState("");

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

		fetch(baseUrl + "/notifications", options)
			.then((response) => response.text())
			.then((result) => {
				if (JSON.parse(result)._embedded) {
					setNotifications(
						JSON.parse(result)._embedded.notificationDTOList
					);
				} else {
					setNotifications("");
				}
			})
			.catch((error) => {
				console.log("error: ", error, "LISTA VJEROJATNO PRAZNA");
				setNotifications("");
			});
	}, []);

	// if (notifications)
	if (true) {
		return (
			<Segment>
				<List divided relaxed>
					{true ? ( //je li pročitan
						<List.Item id="oldNotification">
							Stara obavijest
							{/* tu treba doci {...notification.request} */}
							{true ? ( //je li vezan za zahtjev
								<List.Item
									content={
										<a
											id="linkToRequest"
											href="http://www.semantic-ui.com"
										>
											<List.Icon name="arrow circle right"></List.Icon>
											Odi na zahtjev!
										</a>
									}
								/>
							) : (
								<span>&nbsp;</span>
							)}
						</List.Item>
					) : (
						<List.Item id="newNotification">
							Nova obavijest
							{/* tu treba doci {...notification.request} */}
							{true ? ( //je li vezan za zahtjev
								<List.Item
									content={
										<a
											id="linkToRequest"
											href="http://www.semantic-ui.com"
										>
											<List.Icon name="arrow circle right"></List.Icon>
											Odi na zahtjev!
										</a>
									}
								/>
							) : (
								<span>&nbsp;</span>
							)}
						</List.Item>
					)}
				</List>
			</Segment>
		);
	} else {
		return <div>Nema obavijesti!</div>;
	}
}

export default Notifications;
