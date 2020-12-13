import React, { Component } from "react";
import { HashRouter, Switch, Route } from "react-router-dom";
import "./App.css";
import LoginForm from "./components/LoginForm";
import Header from "./components/Header";
import RegisterForm from "./components/RegisterForm";
import MapView from "./components/MapView";


class App extends Component {
	constructor(props) {
		super(props);

		this.state = {
			isLogedIn: sessionStorage.getItem("isLogedIn") || false,
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
		sessionStorage.setItem("isLogedIn", "true");
		sessionStorage.setItem("basicAuthToken", this.state.basicAuthToken);
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

		return (
			<div className="App">
			  <MapView/>
			</div>
		);

		if (!this.state.isLogedIn) {
			return (
				<div className="flexForCenter">
					<HashRouter>
						<Switch>
							<Route
								exact
								path="/"
								render={(props) => (
									<LoginForm
										{...props}
										setLogInTrueHandler={this.setLogInTrue}
									/>
								)} /* component={LoginForm} */
							/>
							<Route
								exact
								path="/register"
								component={RegisterForm}
							/>
						</Switch>
					</HashRouter>
				</div>
			);
		} else {
			return (
				<div>
					<Header setLogInsFalseHandler={this.setLogInFalse} />
				</div>
			);
		}
	}
}

export default App;
