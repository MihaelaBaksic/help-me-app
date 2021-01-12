import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import MapComponent from "./MapComponent";
import L from "leaflet";
import { useForm } from "react-hook-form";

const baseUrl = "http://localhost:8080";

function AdressChangeCompoment(props) {
	let history = useHistory();
	const { register, handleSubmit, errors } = useForm();
	const [geoLocation, setGeoLocation] = useState(
		L.latLng(45.800623, 15.971131)
	);

	async function onSubmit(values, e) {
		e.preventDefault();

		e.preventDefault();
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		const options = {
			method: "GET",
			headers: myHeaders,
			redirect: "follow",
		};

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
				/* console.log(fetchResult); */
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
				/* console.log(values); */
				values.address = {};
				values.address.description = description;
				/* if (useLocation === "1") */ {
					values.address.x_coord = geoLocation.lat;
					values.address.y_coord = geoLocation.lng;
				} /*  else {
					values.address = null;
				} */

				/* console.log(values); */
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
				fetch(baseUrl + "/user/addressSettings", options)
					.then((response) => response.text())
					.then((result) => {
						/* console.log(JSON.parse(result)); */
						history.push("/myRequests");
					})
					.catch((error) => console.log(error));
			});
	}
	return (
		<div className="container">
			<form
				id="adress"
				name="adress"
				className="forma"
				onSubmit={handleSubmit(onSubmit)}
				onKeyPress={(e) => {
					e.key === "Enter" && e.preventDefault();
				}}
			>
				<h3>Unesite novu lokaciju djelovanja:</h3>
				<MapComponent setGeoLocation={setGeoLocation} />
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
			</form>
		</div>
	);
}
export default AdressChangeCompoment;
