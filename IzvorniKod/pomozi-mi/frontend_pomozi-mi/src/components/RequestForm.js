import React, { useState } from "react";
import { useForm } from "react-hook-form";
import MapComponent from "./MapComponent";
import L from "leaflet";

const baseUrl = "http://localhost:8080";

function RequestForm() {
	const { register, handleSubmit, errors } = useForm();
	const [geoLocation, setGeoLocation] = useState(L.latLng(0, 0));
	/* const onSubmit = (data) => console.log(data); */
	async function onSubmit(values, e) {
		e.preventDefault();

		console.log("hell yeah " + JSON.stringify(values));

		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		myHeaders.append("Content-Type", "application/json");
		const options = {
			method: "POST",
			headers: myHeaders,
			body: JSON.stringify(values),
		};
		fetch(baseUrl + "/requests", options)
			.then((response) => response.text())
			.then((result) => console.log(result))
			.catch((error) => console.log(error));
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
			</form>
			<MapComponent setGeoLocation={setGeoLocation} />
			<div className="loginOrRegisterBtns">
				<button type="cancel" className="btn btn-secondary">
					Cancel
				</button>
				<button
					type="submit"
					className="btn btn-primary"
					form="newRequest"
				>
					Submit
				</button>
			</div>
		</div>
	);
}
export default RequestForm;
