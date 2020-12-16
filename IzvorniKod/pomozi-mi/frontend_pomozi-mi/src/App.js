import React, { Component } from "react";

import { HashRouter, Switch, Route } from "react-router-dom";

import "./App.css";
import LoginForm from "./components/LoginForm";
import RegisterForm from "./components/RegisterForm";
import LogedInUserComponent from "./components/LogedInUserComponent";

//Testiranje
import RequestList from "./components/RequestList";
import DesniStupacKomponenta from "./components/DesniStupacKomponenta";
import GetCurrentUserComponent from "./components/GetCurrentUserComponent";
import ViewProfileComponent from "./components/ViewProfileComponent";

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
		this.setState({
			isLogedIn: true,
			basicAuthToken: btoa(
				unescape(encodeURIComponent(usernameXD + ":" + passwordCF))
			),
		});
		let basicAuthToken = btoa(
			unescape(encodeURIComponent(usernameXD + ":" + passwordCF))
		);
		sessionStorage.setItem("isLogedIn", "true");
		sessionStorage.setItem("basicAuthToken", basicAuthToken);
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
		if (!this.state.isLogedIn && devMode == "OFF") {
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
		} else if (devMode == "OFF") {
			return <LogedInUserComponent setLogInFalse={this.setLogInFalse} />;
		} else {
			return (
				<HashRouter>
					<Switch>
						<Route
							exact
							path="/test/requestList"
							component={RequestList}
						/>
						{/* <Route
							exact
							path="/test/profile"
							component={ViewProfileComponent}
						/> */}
					</Switch>
				</HashRouter>
			);
		}
	}
}

export default App;
