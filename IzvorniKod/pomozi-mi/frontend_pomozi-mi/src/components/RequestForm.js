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
				className="forma"
				onSubmit={handleSubmit(onSubmit)}
			>
				<div id="newRequestFormInputs">
					<div className="form-group">
						<label for="Dokad najkasnije treba pomoć">
							Dokad treba pomoć
						</label>
						<input
							type="datetime-local"
							className="form-control"
							id="rokPomoci"
							placeholder="Unesite datum"
						/>
						<label for="Dokad najkasnije treba pomoć">
							Dokad treba pomoć
						</label>
						<input
							type="datetime-local"
							className="form-control"
							id="rokPomoci"
							placeholder="Unesite datum"
						/>
					</div>

					<div id="komentar" className="form-group">
						<textarea
							name="comment"
							className="form-control"
							id="requestKomentar"
							rows="3"
							placeholder="Komentar"
						/>
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
