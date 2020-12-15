import React, { Component, useEffect, useState } from "react";
import CentarKomponenta from "./CentarKomponenta";
import UserSettings from "./UserSettings";
import DeleteComponent from "./DeleteComponent";

class ViewProfileComponent extends Component {
	constructor() {
		super();
		this.state = {
			showing: "myRequests",
		};
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
								alt="Daniel Adams"
							/>
						</div>
						<div className="author-card-details">
							<h5 className="author-card-name text-lg">
								Dominik Milde
							</h5>
							<span className="author-card-position">
								Administrator
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
							href="#/test/profile"
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
							href="#/test/profile"
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
							href="#/test/profile"
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
							href="#/test/profile"
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
							href="#/test/profile"
						>
							Brisanje korisničkog računa
						</a>
					</nav>
				</div>
			</div>
			//{/* {this.state.showing === "settings" && <UserSettings />}
			//{this.state.showing === "myRequests" && (
			//	<CentarKomponenta />
			//)}
			//{this.state.showing === "delete" && <DeleteComponent />} */}
		);
	}
}

export default ViewProfileComponent;
