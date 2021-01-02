import React, { useEffect, useState } from "react";
import { useHistory, withRouter } from "react-router-dom";

function ViewProfileComponent(props) {
	let history = useHistory();
	let [podaci, setPodaci] = useState("");
	let [showing, setShowing] = useState(/* "requests" */ "");

	console.log(history.location.pathname.substr());

	useEffect(() => {
		setShowing(history.location.pathname.substr(1));
	}, [history.location.pathname]);

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

		fetch("http://localhost:8080/user/getCurrentUser", options)
			.then((response) => response.text())
			.then((result) => {
				console.log(JSON.parse(result));
				setPodaci(JSON.parse(result));
				sessionStorage.setItem(
					"currentUserUsername",
					JSON.parse(result).username
				);
				sessionStorage.setItem(
					"isAdmin",
					JSON.parse(result).administrator
				);
				sessionStorage.setItem(
					"currentUserFirstName",
					JSON.parse(result).firstName
				);
				sessionStorage.setItem(
					"currentUserLastName",
					JSON.parse(result).lastName
				);
				sessionStorage.setItem(
					"currentUserEmail",
					JSON.parse(result).email
				);
			})
			.then(() => {})
			.catch((error) => console.log("error", error));
	}, []);

	return (
		<div className="lijeviStupacContentHolder">
			<div className="author-card">
				<div className="author-card-cover">
					<a
						className="btn btn-style-1 btn-white btn-sm"
						href="#/test/profile"
						data-toggle="tooltip"
					>
						<i className="fa fa-award text-md"></i>
						<span className="fa fa-star checked"></span>
						<span className="fa fa-star checked"></span>
						<span className="fa fa-star checked"></span>
						<span className="fa fa-star"></span>
						<span className="fa fa-star"></span>
					</a>
				</div>
				<div className="author-card-profile">
					<div className="author-card-avatar">
						<img src="https://bootdey.com/img/Content/avatar/avatar1.png" />
					</div>
					<div className="author-card-details">
						<h5 className="author-card-name text-lg">
							{podaci.firstName} {podaci.lastName}
						</h5>
						<span className="author-card-position">
							{podaci.administrator
								? "Administrator"
								: "Korisnik"}
						</span>
					</div>
				</div>
			</div>
			<div className="wizard">
				<nav className="list-group list-group-flush">
					<div
						onClick={() => {
							history.push("/newRequest");
							console.log("/newRequest");
							setShowing("newRequest");
						}}
						className={
							showing === "newRequest"
								? "list-group-item active"
								: "list-group-item"
						}
						role="button"
					>
						<div className="d-flex justify-content-between align-items-center">
							<div className="d-inline-block font-weight-medium text-uppercase">
								Pomoz Bog!!!!
							</div>
						</div>
					</div>

					<div
						onClick={() => {
							history.push("/requests");
							console.log("/requests");
							setShowing("requests");
						}}
						className={
							showing === "requests"
								? "list-group-item active"
								: "list-group-item"
						}
						role="button"
					>
						<div className="d-flex justify-content-between align-items-center">
							<div className="d-inline-block font-weight-medium text-uppercase">
								Pomozi nekome!
							</div>
						</div>
					</div>
					<div
						onClick={() => {
							history.push("/myRequests");
							console.log("/myRequests");
							setShowing("myRequests");
						}}
						className={
							showing === "myRequests"
								? "list-group-item active"
								: "list-group-item"
						}
						role="button"
					>
						<div className="d-flex justify-content-between align-items-center">
							<div className="d-inline-block font-weight-medium text-uppercase">
								Moji zahtjevi
							</div>
							<span className="badge badge-secondary">2</span>
						</div>
					</div>
					<div
						onClick={() => {
							history.push("/otherRequests");
							console.log("/otherRequests");
							setShowing("otherRequests");
						}}
						className={
							showing === "otherRequests"
								? "list-group-item active"
								: "list-group-item"
						}
						role="button"
					>
						<div className="d-flex justify-content-between align-items-center">
							<div className="d-inline-block font-weight-medium text-uppercase">
								Kontaktirani zahtjevi
							</div>
							<span className="badge badge-secondary">6</span>
						</div>
					</div>
					<div
						onClick={() => {
							history.push("/settings");
							console.log("/settings");
							setShowing("settings");
						}}
						className={
							showing === "settings"
								? "list-group-item active"
								: "list-group-item"
						}
						role="button"
					>
						Postavke korisničkog računa
					</div>
					<div
						onClick={() => {
							history.push("/adress");
							console.log("/adress");
							setShowing("adress");
						}}
						className={
							showing === "adress"
								? "list-group-item active"
								: "list-group-item"
						}
						role="button"
					>
						Adresa djelovanja
					</div>
				</nav>
			</div>
		</div>
	);
}

export default withRouter(ViewProfileComponent);
