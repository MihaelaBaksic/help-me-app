import React from "react";
import { useForm } from "react-hook-form";

function UserSettings(props) {
	const { handleSubmit, register, errors, watch } = useForm({});
	function onSubmit(values, e) {
		e.preventDefault();
	}

	return (
		<div className="col-lg-8 pb-5">
			<form className="row">
				<div className="col-md-6">
					<div className="form-group">
						<label for="account-email">E-mail adresa</label>
						<input
							className="form-control"
							type="email"
							id="account-email"
							value={sessionStorage.getItem("currentUserEmail")}
							disabled="true"
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="account-phone">Korisničko ime</label>
						<input
							className="form-control"
							type="text"
							id="account-user"
							value={sessionStorage.getItem(
								"currentUserUsername"
							)}
							disabled="true"
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="firstName">Ime</label>
						<input
							className="form-control"
							type="text"
							id="firstName"
							value={sessionStorage.getItem(
								"currentUserFirstName"
							)}
							required=""
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="lastName">Prezime</label>
						<input
							className="form-control"
							type="text"
							id="lastName"
							value={sessionStorage.getItem(
								"currentUserLastName"
							)}
							required=""
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="phoneNumber">Kontakt broj</label>
						<input
							className="form-control"
							type="text"
							id="phoneNumber"
							value="09548654465"
							required=""
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="account-phone"></label>
					</div>
				</div>
				<div className="col-12">
					<hr className="mt-2 mb-3" />
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="password">Promjena zaporke</label>
						<input
							className="form-control"
							type="password"
							id="password"
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="account-confirm-pass">
							Potvrdi zaporku
						</label>
						<input
							className="form-control"
							type="password"
							id="account-confirm-pass"
						/>
					</div>
				</div>
				<div className="col-12">
					<hr className="mt-2 mb-3" />
					<div className="d-flex flex-wrap justify-content-between align-items-center">
						<button
							className="btn btn-style-1 btn-secondary float-left"
							type="button"
						>
							Cancel
						</button>
						<button
							className="btn btn-style-1 btn-primary"
							type="button"
						>
							Potvrdi
						</button>
					</div>
				</div>
			</form>
		</div>
	);
}
export default UserSettings;
