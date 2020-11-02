import React from "react";
import logo from "./resources/zec.png";

function RegisterForm() {
	const [registrationForm, setRegistrationForm] = React.useState({
		firstName: "",
		lastName: "",
		username: "",
		email: "",
		password: "",
		repeatPassword: "",
		telephone: "",
	});

	function onChange(event) {
		const { name, value } = event.target;
		setRegistrationForm((oldRegistrationForm) => ({
			...oldRegistrationForm,
			[name]: value,
		}));
	}

	function onSubmit(e) {
		e.preventDefault();
		const registerData = {
			firstName: registrationForm.firstName,
			lastName: registrationForm.lastName,
			username: registrationForm.username,
			email: registrationForm.email,
			password: registrationForm.password,
			telephone: registrationForm.telephone,
		};
		const options = {
			method: "POST",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(registerData),
		};

		return fetch("/register", options);
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
			<form onSubmit={onSubmit}>
				<div className="form-group">
					<input
						name="firstName"
						className="form-control"
						placeholder="Ime"
						onChange={onChange}
						value={registrationForm.firstName}
					></input>
				</div>
				<div className="form-group">
					<input
						name="lastName"
						className="form-control"
						placeholder="Prezime"
						onChange={onChange}
						value={registrationForm.lastName}
					></input>
				</div>
				<div className="form-group">
					<input
						name="username"
						className="form-control"
						placeholder="Username"
						onChange={onChange}
						value={registrationForm.username}
					></input>
				</div>
				<div className="form-group">
					<input
						name="email"
						className="form-control"
						placeholder="E-mail"
						onChange={onChange}
						value={registrationForm.email}
					></input>
				</div>
				<div className="form-group">
					<input
						name="password"
						type="password"
						className="form-control"
						placeholder="Lozinka"
						onChange={onChange}
						value={registrationForm.password}
					></input>
				</div>
				<div className="form-group">
					<input
						name="repeatPassword"
						type="password"
						className="form-control"
						placeholder="Potvrdite lozinku"
						onChange={onChange}
						value={registrationForm.repeatPassword}
					></input>
				</div>
				<div className="form-group">
					<input
						name="telephone"
						className="form-control"
						placeholder="Telefon"
						onChange={onChange}
						value={registrationForm.telephone}
					></input>
				</div>

				<div className="loginOrRegisterBtns">
					<button
						type="submit"
						className="btn btn-secondary btn-lg"
						onClick={() => console.log("Napravit redirect")}
					>
						Register
					</button>
				</div>
			</form>
		</div>
	);
}

export default RegisterForm;
