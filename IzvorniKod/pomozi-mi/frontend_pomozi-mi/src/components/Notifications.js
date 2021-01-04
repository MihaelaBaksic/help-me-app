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
import { useHistory, withRouter } from "react-router-dom";

//const baseUrl = `${process.env.PUBLIC_URL}`;
const baseUrl = "http://localhost:8080";
function Notifications(props) {
	const [notifications, setNotifications] = useState("");
	let history = useHistory();

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
				setNotifications(JSON.parse(result));
				console.log(JSON.parse(result));
			})
			.catch((error) => {
				console.log(
					"error: ",
					error,
					"LISTA OBAVIJESTI VJEROJATNO PRAZNA"
				);
				setNotifications("");
			});
	}, []);

	// if (notifications)
	if (notifications) {
		return (
			<Segment id="notificationHolder" className="hiddenScroll">
				<List divided relaxed>
					{console.log(notifications[0])}
					{notifications.map((notif, index) =>
						notif.isRead ? (
							<List.Item
								id="oldNotification"
								key={
									index
								} /* OVO JE PRIVREMENO, NOTIFIKACIJE TREBA OMOTATI DA MOGU PRISTUPIT ID-U */
							>
								<div className="notificationMessage">
									{notif.message}
									<br />
									{notif.request.title}
								</div>

								<List.Item
									content={
										<div
											className="notificationToRequestLink"
											role="button"
											onClick={() =>
												history.push(
													"/request/" +
														notif.request.id
												)
											}
										>
											<List.Icon name="arrow circle right"></List.Icon>
											Odi na zahtjev!
										</div>
									}
								/>
							</List.Item>
						) : (
							<List.Item
								id="newNotification"
								key={
									index
								} /* OVO JE PRIVREMENO, NOTIFIKACIJE TREBA OMOTATI DA MOGU PRISTUPIT ID-U */
							>
								Nova obavijest
								<List.Item
									content={
										<div
											role="button"
											onClick={() =>
												history.push(
													"/request/" +
														notif.request.id
												)
											}
										>
											<List.Icon name="arrow circle right"></List.Icon>
											Odi na zahtjev!
										</div>
									}
								/>
							</List.Item>
						)
					)}
				</List>
			</Segment>
		);
	} else {
		return <div>Nema obavijesti!</div>;
	}
}

export default withRouter(Notifications);
