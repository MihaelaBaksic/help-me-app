import React, { Component } from "react";

import { HashRouter, Switch, Route } from "react-router-dom";

import "./App.css";
import LoginForm from "./components/LoginForm";
import RegisterForm from "./components/RegisterForm";
import LogedInUserComponent from "./components/LogedInUserComponent";

//Testiranje
import RequestList from "./components/RequestList";
import ViewProfileComponent from "./components/ViewProfileComponent";
import RequestForm from "./components/RequestForm";
import MapComponent from "./components/MapComponent";
import OtherUserProfile from "./components/OtherUserProfile";

let devMode = "OFF"; /* "ON" */

class App extends Component {
	constructor(props) {
		super(props);

		this.state = {
			isLogedIn: sessionStorage.getItem("isLogedIn") || false /*true*/,
			basicAuthToken: sessionStorage.getItem("basicAuthToken") || "",
		};

		this.setLogInTrue = this.setLogInTrue.bind(this);
		this.setLogInFalse = this.setLogInFalse.bind(this);
	}

	setLogInTrue(usernameXD, passwordCF) {
		let basicAuthToken = btoa(
			unescape(encodeURIComponent(usernameXD + ":" + passwordCF))
		);
		sessionStorage.setItem("basicAuthToken", basicAuthToken);

		sessionStorage.setItem("isLogedIn", "true");
		this.setState({
			isLogedIn: true,
			basicAuthToken: btoa(
				unescape(encodeURIComponent(usernameXD + ":" + passwordCF))
			),
		});
	}

	setLogInFalse() {
		this.setState({
			isLogedIn: false,
			basicAuthToken: "",
		});
		sessionStorage.clear("isLogedIn");
		sessionStorage.clear("basicAuthToken");
	}

	render() {
		if (!this.state.isLogedIn && devMode === "OFF") {
			return (
				<HashRouter>
					<Switch>
						<Route
							exact
							path="/"
							render={() => (
								<LoginForm setLogInTrue={this.setLogInTrue} />
							)}
						/>
						<Route
							exact
							path="/register"
							component={RegisterForm}
						/>
					</Switch>
				</HashRouter>
			);
		} else if (devMode === "OFF") {
			return (
				<HashRouter>
					<Switch>
						<Route
							path="/"
							render={() => (
								<LogedInUserComponent
									setLogInFalse={this.setLogInFalse}
								/>
							)}
						/>
					</Switch>
				</HashRouter>
			);
		} else {
			return (
				<HashRouter>
					<Switch>
						<Route
							exact
							path="/test/requestList"
							component={RequestList}
						/>
						<Route
							exact
							path="/test/profile"
							component={ViewProfileComponent}
						/>
						<Route
							exact
							path="/test/newRequest"
							component={RequestForm}
						/>
						<Route
							exact
							path="/test/map"
							component={MapComponent}
						/>
						<Route
							exact
							path="/test/userother"
							component={OtherUserProfile}
						/>
					</Switch>
				</HashRouter>
			);
		}
	}
}

export default App;
