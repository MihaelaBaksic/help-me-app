import React, { useState } from "react";
import { useForm } from "react-hook-form";
import logo from "./resources/todo_logo.png";

//za Dev 8080, production 8080 tj. `${process.env.PUBLIC_URL}`
const registerUrl = "http://localhost:8080/register";
//const registerUrl = `${process.env.PUBLIC_URL}/register`;

function RegisterForm(props) {
	const { handleSubmit, register, errors, watch } = useForm({});

	const [errorMessage, setErrorMessage] = useState("");

	async function onSubmit(values, e) {
		e.preventDefault();
		delete values["confirmPassword"];
		console.log(values);
		const options = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(values),
		};
		//frontend dev
		/* const response =  */
		await fetch(registerUrl, options).then((response) => {
			if (response.status === 200) {
				props.history.push("/");
			} else {
				response.json().then((result) => {
					setErrorMessage(result.message);
				});
			}
		});
	}

	return (
		<div>
			<div className="formHeader">
				<img className="formLogo" src={logo} alt="neradi mi slika" />
				<div className="kratkiOpis">Spremno iščekujemo Vašu pomoć</div>
			</div>
			<div className="kratkiOpis">Unesite Vaše podatke</div>
			<div>
				<br />
			</div>
			<form onSubmit={handleSubmit(onSubmit)}>
				<div className="form-group">
					<input
						name="firstName"
						className="form-control"
						placeholder="Ime"
						ref={register({
							minLength: { value: 2, message: "Prekratko ime" },
							maxLength: { value: 30, message: "Predugačko ime" },
							required: {
								value: "Required",
								message: "Ime je obavezno",
							},
						})}
					/>
					<div className="error-message">
						{errors.firstName && errors.firstName.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="lastName"
						className="form-control"
						placeholder="Prezime"
						ref={register({
							minLength: {
								value: 2,
								message: "Prekratko prezime",
							},
							maxLength: {
								value: 30,
								message: "Predugačko prezime",
							},
							required: {
								value: "Required",
								message: "Prezime je obavezno",
							},
						})}
					/>
					<div className="error-message">
						{errors.lastName && errors.lastName.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="username"
						className="form-control"
						placeholder="Korisničko ime"
						ref={register({
							minLength: {
								value: 4,
								message: "Prekratak nadimak",
							},
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
						placeholder="Lozinka"
						ref={register({
							required: {
								value: "Required",
								message: "Lozinka je obavezna",
							},
							minLength: {
								value: 8,
								message:
									"Duljina lozinke mora biti najmanje 8 znakova",
							},
						})}
					/>
					<div className="error-message">
						{errors.password && errors.password.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="confirmPassword"
						type="password"
						className="form-control"
						placeholder="Potvrdite lozinku"
						ref={register({
							required: {
								value: "Required",
								message: "Obavezno potvrdite lozinku",
							},
							validate: (value) =>
								value === watch("password") ||
								"Lozinke se ne poklapaju",
						})}
					/>
					<div className="error-message">
						{errors.confirmPassword &&
							errors.confirmPassword.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="email"
						className="form-control"
						placeholder="E-mail"
						ref={register({
							required: {
								value: "Required",
								message: "Email je obavezan",
							},
							pattern: {
								value: /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i,
								message: "Email adresa nije valjana",
							},
						})}
					/>
					<div className="error-message">
						{errors.email && errors.email.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="phoneNumber"
						className="form-control"
						placeholder="Telefon"
						ref={register({
							required: {
								value: "Required",
								message: "Broj telefona je obavezan",
							},
							maxLength: {
								value: 15,
								message: "Predugačak broj telefona",
							},
							pattern: {
								value: /^\d+$/i,
								message: "Unesite ispravan telefonski broj",
							},
						})}
					/>
					<div className="error-message">
						{errors.phoneNumber && errors.phoneNumber.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="streetName"
						className="form-control"
						placeholder="Ime ulice"
						ref={register({
							minLength: {
								value: 2,
								message: "Prekratko ime ulice",
							},
							required: {
								value: "Required",
								message: "Ime ulice je obavezno",
							},
						})}
					/>
					<div className="error-message">
						{errors.streetName && errors.streetName.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="streetNumber"
						className="form-control"
						placeholder="Kućni broj"
						ref={register({
							required: {
								value: "Required",
								message: "Kućni broj je obavezan",
							},
							pattern: {
								value: /^\d+$/i,
								message: "Unesite ispravan kućni broj",
							},
						})}
					/>
					<div className="error-message">
						{errors.streetNumber && errors.streetNumber.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="cityName"
						className="form-control"
						placeholder="Mjesto stanovanja"
						ref={register({
							minLength: {
								value: 2,
								message: "Prekratko ime prebivališta",
							},
							required: {
								value: "Required",
								message: "Mjesto stanovanja je obavezno",
							},
						})}
					/>
					<div className="error-message">
						{errors.cityName && errors.cityName.message}
					</div>
				</div>
				<div className="form-group">
					<input
						name="cityCode"
						className="form-control"
						placeholder="Poštanski broj"
						ref={register({
							required: {
								value: "Required",
								message: "Poštanski broj je obavezan",
							},
							pattern: {
								value: /^\d+$/i,
								message: "Unesite ispravan kućni broj",
							},
						})}
					/>
					<div className="error-message">
						{errors.cityCode && errors.cityCode.message}
					</div>
				</div>

				<div className="loginOrRegisterBtns">
					<div>
						<button
							type="submit"
							className="btn btn-secondary btn-lg"
						>
							Register
						</button>
					</div>
					<div className="api_error_message">{errorMessage}</div>
				</div>
			</form>
		</div>
	);
}

export default RegisterForm;
