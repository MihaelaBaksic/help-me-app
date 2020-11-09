import React /* , { Component }  */ from "react";
import { HashRouter, BrowserRouter, Switch, Route } from "react-router-dom";
import "./App.css";
import LoginForm from "./components/LoginForm";
import Header from "./components/Header";
import RegisterForm from "./components/RegisterForm";

function App() {
	let isLoggedIn = false;

	if (!isLoggedIn) {
		return (
			<div className="formHolder">
				<HashRouter>
					<Switch>
						<Route path="/" exact component={LoginForm} />
						<Route
							path="/register"
							exact
							component={RegisterForm}
						/>
					</Switch>
				</HashRouter>
			</div>
		);
	}

	return (
		<div>
			<Header></Header>
		</div>
	);
}

export default App;
