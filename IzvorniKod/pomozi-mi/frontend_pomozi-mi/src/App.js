import React, { Component, useState /* , { Component }  */ } from "react";
import { HashRouter, BrowserRouter, Switch, Route } from "react-router-dom";
import "./App.css";
import LoginForm from "./components/LoginForm";
import Header from "./components/Header";
import RegisterForm from "./components/RegisterForm";
import GetCurrentUserComponent from "./components/GetCurrentUserComponent";

class App extends Component {
	constructor(props) {
		super(props);

		this.state = {
			isLogedIn: sessionStorage.getItem("isLogedIn") || false,
			username: sessionStorage.getItem("username") || "",
			password: sessionStorage.getItem("password") || "",
		};

		this.setLogInTrue = this.setLogInTrue.bind(this);
	}

	setLogInTrue(usernameXD, passwordCF) {
		this.setState({
			isLogedIn: true,
			username: usernameXD,
			password: passwordCF,
		});
		sessionStorage.setItem("isLogedIn", "true");
		sessionStorage.setItem("username", usernameXD);
		sessionStorage.setItem("password", passwordCF);
	}

	render() {
		if (!this.state.isLogedIn) {
			return (
				<div className="formHolder">
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
					<Header></Header>
					<GetCurrentUserComponent
						username={this.state.username}
						password={this.state.password}
					></GetCurrentUserComponent>
				</div>
			);
		}
	}
}

export default App;
