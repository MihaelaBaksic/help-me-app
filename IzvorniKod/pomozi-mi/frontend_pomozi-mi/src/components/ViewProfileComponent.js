import React, { Component, useEffect, useState } from "react";
import PropTypes from "prop-types";
import { withRouter } from "react-router-dom";

/* function ViewProfileComponent(props) { */
class ViewProfileComponent extends Component {
	constructor() {
		super();
		this.state = {
			showing: "requests",
			podaci: [],
		};
	}
	static propTypes = {
		match: PropTypes.object.isRequired,
		location: PropTypes.object.isRequired,
		history: PropTypes.object.isRequired,
	};
	componentDidMount() {
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
			.then((result) => this.setState({ podaci: JSON.parse(result) }))
			.then(() => {
				console.log(this.state.podaci);
				sessionStorage.setItem(
					"currentUserUsername",
					this.state.podaci.username
				);
			})
			.catch((error) => console.log("error", error));
	}
	changeState(s) {
		this.setState({
			showing: s,
		});
	}
	promjeniSranje(sranje) {
		this.changeState(sranje);
		console.log(sranje);
		this.props.changeState(sranje);
	}
	render() {
		const { podaci } = this.state.podaci;
		const { match, location, history } = this.props;

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
								{this.state.podaci.firstName}
								{this.state.podaci.lastName}
							</h5>
							<span className="author-card-position">
								{this.state.podaci.administrator
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
								this.promjeniSranje("newRequest");
							}}
							className={
								this.state.showing === "newRequest"
									? "list-group-item active"
									: "list-group-item"
							}
							href="#/"
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
								this.promjeniSranje("requests");
							}}
							className={
								this.state.showing === "requests"
									? "list-group-item active"
									: "list-group-item"
							}
							href="#/"
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
								this.promjeniSranje("myRequests");
							}}
							className={
								this.state.showing === "myRequests"
									? "list-group-item active"
									: "list-group-item"
							}
							href="#/"
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
								this.promjeniSranje("otherRequests");
							}}
							className={
								this.state.showing === "otherRequests"
									? "list-group-item active"
									: "list-group-item"
							}
							href="#/"
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
								this.promjeniSranje("settings");
							}}
							className={
								this.state.showing === "settings"
									? "list-group-item active"
									: "list-group-item"
							}
							href="#/"
						>
							Postavke korisničkog računa
						</div>
						<div
							onClick={() => {
								history.push("/adress");
								console.log("/adress");
								this.promjeniSranje("adress");
							}}
							className={
								this.state.showing === "adress"
									? "list-group-item active"
									: "list-group-item"
							}
							href="#/"
						>
							Adresa djelovanja
						</div>
					</nav>
				</div>
			</div>
		);
	}
}

export default withRouter(ViewProfileComponent);
