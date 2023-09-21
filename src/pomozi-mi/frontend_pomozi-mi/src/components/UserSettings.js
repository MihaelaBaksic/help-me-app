import React, { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useHistory, withRouter } from "react-router-dom";

const baseUrl = `${process.env.PUBLIC_URL}`;
//const baseUrl = "http://localhost:8080";

function UserSettings(props) {
	let history = useHistory();
	const { handleSubmit, register, errors, watch } = useForm({});
	const [ime, setIme] = useState("");
	const [prezime, setPrezime] = useState("");

	useEffect(() => {
		setIme(sessionStorage.getItem("currentUserFirstName"));
	}, []);
	useEffect(() => {
		setPrezime(sessionStorage.getItem("currentUserLastName"));
	}, []);

	function onSubmit(values, e) {
		e.preventDefault();

		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		myHeaders.append("Content-Type", "application/json");

		let prepBody = {};
		prepBody.password = values.password;
		/* console.log(values.firstName); */
		if (values.firstName) {
			prepBody.firstName = values.firstName;
		}
		if (values.lastName) {
			prepBody.lastName = values.lastName;
		}
		if (values.phoneNumber) {
			prepBody.phoneNumber = values.phoneNumber;
		}
		/* console.log(prepBody); */
		const options = {
			method: "POST",
			headers: myHeaders,
			body: JSON.stringify(prepBody),
		};
		fetch(baseUrl + "/user/settings", options)
			.then((response) => {
				if (response.status === 201) {
					return response.text();
				}
			})
			.then((result) => {
				/* console.log(JSON.parse(result)); */

				sessionStorage.setItem(
					"currentUserFirstName",
					JSON.parse(result).firstName
				);
				sessionStorage.setItem(
					"currentUserLastName",
					JSON.parse(result).lastName
				);

				setPrezime(JSON.parse(result).lastName);
				setIme(JSON.parse(result).firstName);

				let basicAuthToken = btoa(
					unescape(
						encodeURIComponent(
							sessionStorage.getItem("currentUserUsername") +
								":" +
								prepBody.password
						)
					)
				);
				sessionStorage.setItem("basicAuthToken", basicAuthToken);
				//refresh page
				history.go(0);
			})
			.catch((error) => console.log("error", error));
	}

	return (
		<div className="col-lg-8 pb-5 korisnickePostavke">
			<form className="row" onSubmit={handleSubmit(onSubmit)}>
				<div className="col-md-6">
					<div className="form-group">
						<label htmlFor="account-email">E-mail adresa</label>
						<input
							className="form-control"
							type="email"
							id="account-email"
							value={sessionStorage.getItem("currentUserEmail")}
							disabled={true}
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label htmlFor="account-user">Korisničko ime</label>
						<input
							className="form-control"
							type="text"
							id="account-user"
							value={sessionStorage.getItem(
								"currentUserUsername"
							)}
							disabled={true}
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label htmlFor="firstName">Ime</label>
						<input
							id="firstName"
							name="firstName"
							type="text"
							className="form-control"
							/* placeholder="Ime" */
							placeholder={ime}
							ref={register({
								minLength: {
									value: 2,
									message: "Prekratko ime",
								},
								maxLength: {
									value: 30,
									message: "Predugačko ime",
								},
							})}
						/>
						<div className="error-message">
							{errors.firstName && errors.firstName.message}
						</div>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<div className="form-group">
							<label htmlFor="lastName">Prezime</label>
							<input
								name="lastName"
								type="text"
								id="lastName"
								className="form-control"
								/* placeholder="Prezime" */
								placeholder={prezime}
								ref={register({
									minLength: {
										value: 2,
										message: "Prekratko prezime",
									},
									maxLength: {
										value: 30,
										message: "Predugačko prezime",
									},
								})}
							/>
							<div className="error-message">
								{errors.lastName && errors.lastName.message}
							</div>
						</div>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label htmlFor="phoneNumber">Kontakt broj</label>
						<input
							name="phoneNumber"
							className="form-control"
							placeholder="Telefon"
							ref={register({
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
				</div>

				<div className="col-12">
					<hr className="mt-2 mb-3" />
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label htmlFor="password">
							Potvrda izmjena/Promjena lozinke
						</label>
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
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label htmlFor="account-confirm-pass">
							Potvrdi lozinku
						</label>
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
				</div>
				<div className="col-12">
					<hr className="mt-2 mb-3" />
					<div className="d-flex flex-wrap justify-content-between align-items-center">
						<button
							className="btn btn-style-1 btn-secondary float-left"
							type="button"
							onClick={() => {
								history.push("/requests");
							}}
						>
							Cancel
						</button>
						<button
							className="btn btn-style-1 btn-primary"
							type="submit"
						>
							Potvrdi
						</button>
					</div>
				</div>
			</form>
		</div>
	);
}
export default withRouter(UserSettings);
