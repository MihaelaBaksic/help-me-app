import React, { useState } from "react";
import { useForm } from "react-hook-form";
import logo from "./resources/todo_logo.png";
import { useHistory } from "react-router-dom";
import L from "leaflet";

import { Card } from "semantic-ui-react";
import MapComponent from "./MapComponent";

//za Dev 8080, production 8080 tj. `${process.env.PUBLIC_URL}`
const registerUrl = "http://localhost:8080/register";
//const registerUrl = `${process.env.PUBLIC_URL}/register`;

function RegisterForm(props) {
	const { handleSubmit, register, errors, watch } = useForm({});

	const [errorMessage, setErrorMessage] = useState("");
	const [geoLocation, setGeoLocation] = useState(L.latLng(0, 0));

	let history = useHistory();

	function onSubmit(values, e) {
		e.preventDefault();
		console.log(geoLocation);
		let desciption = "";
		let options222 = {
			method: "GET",
		};
		let fetchResult;
		fetch(
			"https://nominatim.openstreetmap.org/reverse.php?lat=" +
				geoLocation.lat +
				"&lon=" +
				geoLocation.lng +
				"&zoom=18&format=jsonv2",
			options222
		)
			.then((response) => response.text())
			.then((result) => {
				fetchResult = JSON.parse(result);
				console.log(fetchResult);
			})
			.catch((error) => console.log(error.message))
			.finally(() => {
				if (fetchResult.address !== undefined) {
					desciption =
						fetchResult.address.street ||
						fetchResult.address.squares ||
						fetchResult.address.farms ||
						fetchResult.address.localities ||
						fetchResult.address.neighbourhood ||
						fetchResult.address.suburb ||
						fetchResult.address.town ||
						fetchResult.address.city ||
						fetchResult.address.county ||
						fetchResult.address.state ||
						fetchResult.address.country ||
						"";
				} else {
					desciption = "Unknown";
				}
				delete values["confirmPassword"];

				values = JSON.parse(JSON.stringify(values));
				values.description = desciption;
				values.x_coord = geoLocation.lat;
				values.y_coord = geoLocation.lng;

				console.log(values);
				values = JSON.stringify(values);
				const options = {
					method: "POST",
					headers: {
						"Content-Type": "application/json",
					},
					body: values,
				};
				fetch(registerUrl, options).then((response) => {
					if (response.status === 200) {
						console.log("Uspješna registracija");
						history.push("/");
					} else {
						response.json().then((result) => {
							setErrorMessage(result.message);
						});
					}
				});
			});
	}

	return (
		<Card id="registerHolder">
			<div className="formHeader">
				<img className="formLogo" src={logo} alt="neradi mi slika" />
				<div className="kratkiOpis">Unesite Vaše podatke</div>
			</div>

			<form
				className="forma"
				onSubmit={handleSubmit(onSubmit)}
				onKeyPress={(e) => {
					e.key === "Enter" && e.preventDefault();
				}}
			>
				<div className="form-group">
					<input
						name="firstName"
						className="form-control"
						placeholder="Ime"
						ref={register({
							minLength: {
								value: 2,
								message: "Prekratko ime",
							},
							maxLength: {
								value: 30,
								message: "Predugačko ime",
							},
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
				{/* <div className="form-group">
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
				</div> */}

				<MapComponent setGeoLocation={setGeoLocation} />
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
		</Card>
	);
}

export default RegisterForm;
