import React from "react";
import { useForm } from "react-hook-form";

import { Card } from "semantic-ui-react";

import logo from "./resources/todo_logo.png";
function RequestForm() {
	const { register, handleSubmit, errors } = useForm();
	const onSubmit = (data) => console.log(data);
	console.log(errors);

	return (
		<Card>
			<form
				id="newRequest"
				className="forma"
				onSubmit={handleSubmit(onSubmit)}
			>
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
				</div>

				<div className="form-group">
					<textarea
						className="form-control"
						id="requestKomentar"
						rows="3"
						placeholder="Komentar"
					></textarea>
				</div>
				<div className="loginOrRegisterBtns">
					<button type="submit" className="btn btn-primary">
						Submit
					</button>
				</div>
			</form>
		</Card>
	);
}
export default RequestForm;
