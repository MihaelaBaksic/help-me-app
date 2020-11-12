import React from "react";
import { useForm } from "react-hook-form";
import logo from "./resources/todo_logo.png";

function LoginForm(props) {

	const loginUrl = "http://localhost:8080/login";

	function onRegister() {
		props.history.push("/register");
	}

	const { handleSubmit, register, errors } = useForm({});

	function onSubmit(values, e) {
		e.preventDefault();

		var urlEncoded = new URLSearchParams();
		urlEncoded.append("username", values.username);
		urlEncoded.append("password", values.password);

		const options = {
			method: "POST",
			credentials: "same-origin",
			headers: {
				"Content-Type": "application/x-www-form-urlencoded",
			},
			body: urlEncoded,
		};
		console.log(options);
		await fetch(loginUrl, options).then((response) => {
			if (response.status === 200) {
				props.setLogInTrueHandler(values.username, values.password);
			} else {
				console.log("Neuspješan login");
			}
		});
	}

	return (
		<div className="formHolder">
			<div className="formHeader">
				<img className="formLogo" src={logo} alt="neradi mi slika" />
				<div className="kratkiOpis">Spremno iščekujemo Vašu pomoć</div>
			</div>
			<form onSubmit={handleSubmit(onSubmit)}>
				<div className="form-group">
					<input
						name="username"
						className="form-control"
						placeholder="Unesite username"
						ref={register({
							maxLength: {
								value: 30,
								message: "Predugačko korisničko ime",
							},
							required: {
								value: "Required",
								message: "Korisničko ime je obavezno",
							},
						})}
					/>
					<div className="error-message">
						{errors.username && errors.username.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="password"
						type="password"
						className="form-control"
						placeholder="Unesite lozinku"
						ref={register({
							required: {
								value: "Required",
								message: "Lozinka je obavezna",
							},
						})}
					/>
					<div className="error-message">
						{errors.password && errors.password.message}
					</div>
				</div>

				{/* <div className="form-group form-check">
					<input type="checkbox" className="form-check-input" />
					<label className="form-check-label">Zapamti me?</label>
					</div> */}
				<div className="loginOrRegisterBtns">
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
