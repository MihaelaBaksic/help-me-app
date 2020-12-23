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

		e.preventDefault();
		console.log(geoLocation);
		let description = "";
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
					description =
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
					description = "Unknown";
				}

				values = JSON.parse(JSON.stringify(values));
				console.log(values);
				values.address = {};
				values.address.description = description;
				values.address.x_coord = geoLocation.lat;
				values.address.y_coord = geoLocation.lng;

				console.log(values);
				values = JSON.stringify(values);
				var myHeaders = new Headers();
				myHeaders.append(
					"Authorization",
					"Basic " + sessionStorage.getItem("basicAuthToken")
				);
				myHeaders.append("Content-Type", "application/json");
				const options = {
					method: "POST",
					headers: myHeaders,
					body: values,
				};
				fetch(baseUrl + "/requests", options)
					.then((response) => response.text())
					.then((result) => console.log(JSON.parse(result)))
					.catch((error) => console.log(error));
			});
	}

	return (
		<div className="card" id="newRequestCard">
			<form
				id="newRequest"
				name="newRequest"
				className="forma"
				onSubmit={handleSubmit(onSubmit)}
			>
				<div id="newRequestFormInputs" className="naslov_istek">
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

					<div id="requestFormDescription" className="form-group">
						<label>Opis zahtjeva:</label>
						<textarea
							name="description"
							className="form-control"
							rows="3"
							placeholder="Opis"
							ref={register}
						/>
					</div>
				</div>
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
			</form>
		</div>
	);
}
export default RequestForm;
