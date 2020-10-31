import React from "react";
import logo from "./resources/zec.png";
//import { Link } from "react-router-dom";

function LoginForm(props) {
	function onRegister() {
		props.history.push("/register");
	}

	return (
		<div>
			<div className="formHeader">
				<img className="formLogo" src={logo} alt="neradi mi slika" />
				<div className="kratkiOpis">Spremno iščekujemo Vašu pomoć</div>
			</div>
			<form>
				<div className="form-group">
					<input
						name="username"
						className="form-control"
						placeholder="Unesite username"
					></input>
				</div>
				<div className="form-group">
					<input
						name="password"
						type="password"
						className="form-control"
						placeholder="Unesite lozinku"
					></input>
				</div>

				<div className="form-group form-check">
					<input type="checkbox" className="form-check-input" />
					<label className="form-check-label">Zapamti me?</label>
				</div>
				<div className="loginRegisterGumbi">
					<button type="submit" className="btn btn-primary btn-lg">
						Login
					</button>
					<button
						type="button"
						className="btn btn-secondary btn-lg"
						onClick={() => onRegister()}
					>
						Register
					</button>
				</div>
			</form>
		</div>
	);
}

export default LoginForm;
