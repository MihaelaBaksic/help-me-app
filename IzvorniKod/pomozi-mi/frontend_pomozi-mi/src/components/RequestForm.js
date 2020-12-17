import React from "react";
import { useForm } from "react-hook-form";
import MapComponent from "./MapComponent";

const baseUrl = "http://localhost:8080";

function RequestForm() {
	const { register, handleSubmit, errors } = useForm();
	/* const onSubmit = (data) => console.log(data); */
	async function onSubmit(values, e) {
		e.preventDefault();
		class address {
			constructor(streetName, streetNumber, zipCode, locationName) {
				this.streetName = streetName;
				this.streetNumber = streetNumber;
				this.zipCode = zipCode;
				this.locationName = locationName;
			}
		}
		class newRequest {
			constructor(expirationDate, title, description, address) {
				this.expirationDate = expirationDate;
				this.title = title;
				this.description = description;
				this.address = address;
			}
		}
		let requestAddress = new address(
			values.streetName,
			values.streetNumber,
			values.zipCode,
			values.locationName
		);
		let preparedRequest = new newRequest(
			values.expirationDate,
			values.title,
			values.description,
			requestAddress
		);
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		myHeaders.append("Content-Type", "application/json");
		const options = {
			method: "POST",
			headers: myHeaders,
			body: JSON.stringify(preparedRequest),
		};
		await fetch(baseUrl + "/requests", options);

		console.log(JSON.parse(JSON.stringify(preparedRequest)));
	}

	return (
		<div className="card" id="newRequestCard">
			<form
				id="newRequest"
				name="newRequest"
				className="forma"
				onSubmit={handleSubmit(onSubmit)}
			>
				<div id="newRequestFormInputs">
					<div className="form-group">
						<label>Naslov zahtjeva</label>
						<input
							type="text"
							name="title"
							className="form-control"
							id="title"
							placeholder="Naslov"
							ref={register({
								minLength: {
									value: 2,
									message: "Prekratak naslov",
								},
								required: {
									value: "Required",
									message: "Naslov je obavezan",
								},
							})}
						/>
						<div className="error-message">
							{errors.title && errors.title.message}
						</div>
					</div>
					<div className="form-group">
						<label>Istek zahtjeva</label>
						<input
							name="expirationDate"
							type="datetime-local"
							className="form-control"
							id="expirationDate"
							placeholder=""
							ref={register({
								required: {
									value: "Required",
									message: "Datum isteka je obavezan",
								},
							})}
						/>
						<div className="error-message">
							{errors.expirationDate &&
								errors.expirationDate.message}
						</div>
					</div>

					<label>Adresa: </label>

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
							name="locationName"
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
							{errors.locationName && errors.locationName.message}
						</div>
					</div>
					<div className="form-group">
						<input
							name="zipCode"
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
							{errors.zipCode && errors.zipCode.message}
						</div>
					</div>
					<div className="form-group">
						<label>Opis zahtjeva:</label>
						<textarea
							name="description"
							className="form-control"
							id="exampleFormControlTextarea1"
							rows="3"
							placeholder="Opis"
							ref={register}
						/>
					</div>
				</div>
				<MapComponent />
				<div className="loginOrRegisterBtns">
					<button type="cancel" className="btn btn-secondary">
						Cancel
					</button>
					<button type="submit" className="btn btn-primary">
						Submit
					</button>
				</div>
			</form>
		</div>
	);
}
export default RequestForm;
