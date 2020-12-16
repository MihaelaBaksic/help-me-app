import React from "react";
import { useForm } from "react-hook-form";

function RequestForm() {
	const { register, handleSubmit, errors } = useForm();
	const onSubmit = (data) => console.log(data);
	console.log(errors);

	return (
		<div className="card" id="newRequestCard">
			<form
				id="newRequest"
				name="newRequest"
				className="forma"
				onSubmit={handleSubmit(onSubmit)}
			>
				<div id="newRequestFormInputs">
					<div class="form-group">
						<label>Naslov zahtjeva</label>
						<input
							type="text"
							name="title"
							class="form-control"
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
					<div class="form-group">
						<label>Istek zahtjeva</label>
						<input
							name="expirationDate"
							type="datetime-local"
							class="form-control"
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
					<div class="form-group">
						<label>Adresa: </label>
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
					<div class="form-group">
						<label>Opis:</label>
						<textarea
							form="newRequest"
							name="description"
							class="form-control"
							id="description"
							rows="3"
						></textarea>
					</div>
				</div>
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
