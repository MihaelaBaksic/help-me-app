import React, { Component } from "react";
//import { BrowserRouter, Switch, Route } from "react-router-dom";
import "./App.css";
import LoginForm from "./components/LoginForm";
import Header from "./components/Header";

function App() {
	let isLoggedIn = true;

	if (!isLoggedIn) {
		return (
			<div className="formHolder">
				<LoginForm></LoginForm>
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
