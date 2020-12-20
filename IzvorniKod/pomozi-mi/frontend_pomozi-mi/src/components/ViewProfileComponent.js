import React, { Component, useEffect, useState } from "react";
class ViewProfileComponent extends Component {
	
	constructor() {
		super();
		this.state = {
			showing: "myRequests",
			podaci: []
		};
	}
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
			.then((result) => this.setState({"podaci" : JSON.parse(result)}))
			.then(() => console.log(this.state.podaci)) 
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
		const {podaci} = this.state.podaci;

		return (
			<div className="">
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
							<img
								src="https://bootdey.com/img/Content/avatar/avatar1.png"
							/>
						</div>
						<div className="author-card-details">
							<h5 className="author-card-name text-lg">
								{this.state.podaci.firstName} {this.state.podaci.lastName}
							</h5>
							<span className="author-card-position">
								{this.state.podaci.administrator ? "Administrator": "Korisnik"}
							</span>
						</div>
					</div>
				</div>
				<div className="wizard">
					<nav className="list-group list-group-flush">
						<a
							onClick={() => this.promjeniSranje("myRequests")}
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
						</a>
						<a
							onClick={() => this.promjeniSranje("otherRequests")}
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
						</a>
						<a
							onClick={() => this.promjeniSranje("settings")}
							className={
								this.state.showing === "settings"
									? "list-group-item active"
									: "list-group-item"
							}
							href="#/"
						>
							Postavke korisničkog računa
						</a>
						<a
							onClick={() => this.promjeniSranje("adress")}
							className={
								this.state.showing === "adress"
									? "list-group-item active"
									: "list-group-item"
							}
							href="#/"
						>
							Adresa djelovanja
						</a>
						<a
							onClick={() => this.promjeniSranje("delete")}
							className={
								this.state.showing === "delete"
									? "list-group-item active"
									: "list-group-item"
							}
							href="#/"
						>
							Brisanje korisničkog računa
						</a>
					</nav>
				</div>
			</div>
		);
	}
}

export default ViewProfileComponent;
