import React, { Component } from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import "./App.css";
import LoginForm from "./components/LoginForm";
import Header from "./components/Header";
import RegisterForm from "./components/RegisterForm"

function App() {

	//Ako ostavim ovako onda ne mogu sa register button-a otici na 
	// let isLoggedIn = false;

	// if (!isLoggedIn) {
	// 	return (
	// 		<div className="formHolder">
	// 			<LoginForm></LoginForm>
	// 		</div>
	// 	);
	// }

	return(
		<div className = "formHolder">
			<BrowserRouter>
				<Switch>
					<Route path = '/' exact component = {LoginForm} />
					<Route path = '/register' exact component = {RegisterForm} />
				</Switch>
			</BrowserRouter>
		</div>
	)

	return (
		<div>
			<Header></Header>
		</div>
	);
}

export default App;
