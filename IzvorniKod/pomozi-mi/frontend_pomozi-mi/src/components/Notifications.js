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
	let history = useHistory();
	const [notifications, setNotifications] = useState("");
	const [notifOpen, setNotifOpen] = useState("closed");
	const [refreshNotifList, setRefreshNotifList] = useState("");

	function toggleNotifs() {
		if (notifOpen === "closed") {
			setNotifOpen("open");
		} else {
			setNotifOpen("closed");
		}
	}
	useEffect(() => {
		if (notifOpen === "open") {
			if (refreshNotifList === "") {
				setRefreshNotifList("1");
			} else {
				setRefreshNotifList("");
			}
		}
	}, [notifOpen]);

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
	}, [refreshNotifList]);

	// if (notifications)
	if (notifications) {
		if (notifOpen === "open") {
			return (
				<Segment id="notificationHolder" className="hiddenScroll">
					<button className="ui button" onClick={toggleNotifs}>
						Obavijesti
					</button>
					<List divided relaxed>
						{console.log(notifications[0])}
						{notifications.map((notif, index) =>
							!notif.isRead || notif.status === "NOTRATED" ? (
								<List.Item
									id="newNotification"
									key={
										index
									} /* OVO JE PRIVREMENO, NOTIFIKACIJE TREBA OMOTATI DA MOGU PRISTUPIT ID-U */
								>
									<div className="notificationMessage">
										{notif.message}
										<br />
										{notif.request
											? notif.request.title
											: null}
									</div>
									<List.Item
										content={
											<div
												className="notificationToRequestLink"
												role="button"
												onClick={() => {
													notif.isRead = "true";
													history.push(
														"/request/" +
															notif.request.id
													);
												}}
											>
												<List.Icon name="arrow circle right"></List.Icon>
												Odi na zahtjev!
											</div>
										}
									/>
								</List.Item>
							) : (
								<List.Item
									id="oldNotification"
									key={
										index
									} /* OVO JE PRIVREMENO, NOTIFIKACIJE TREBA OMOTATI DA MOGU PRISTUPIT ID-U */
								>
									<div className="notificationMessage">
										{notif.message}
										<br />
										{notif.request
											? notif.request.title
											: null}
										{notif.isRead}
									</div>
									<List.Item
										content={
											<div
												className="notificationToRequestLink"
												role="button"
												onClick={() =>
													notif.request
														? history.push(
																"/request/" +
																	notif
																		.request
																		.id
														  )
														: console.log(
																"zahtjev ne postoji više"
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
			return (
				<Segment
					id=""
					className="hiddenScroll"
					style={{ padding: 5 + "px" }}
				>
					<button className="ui button" onClick={toggleNotifs}>
						Obavijesti
					</button>
				</Segment>
			);
		}
	} else {
		return <div>Nema obavijesti!</div>;
	}
}

export default withRouter(Notifications);
