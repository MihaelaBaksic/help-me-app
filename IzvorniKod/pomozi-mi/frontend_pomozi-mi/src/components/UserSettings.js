function UserSettings(props) {
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
							value={sessionStorage.getItem("currentUserUsername")}
							disabled="true"
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="account-fn">Ime</label>
						<input
							className="form-control"
							type="text"
							id="account-fn"
							value={sessionStorage.getItem("currentUserFirstName")}
							required=""
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="account-ln">Prezime</label>
						<input
							className="form-control"
							type="text"
							id="account-ln"
							value={sessionStorage.getItem("currentUserLastName")}
							required=""
						/>
					</div>
				</div>
				<div className="col-md-6">
					<div className="form-group">
						<label for="account-phone">Kontakt broj</label>
						<input
							className="form-control"
							type="text"
							id="account-phone"
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
						<label for="account-pass">Promjena zaporke</label>
						<input
							className="form-control"
							type="password"
							id="account-pass"
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
						<div className="custom-control custom-checkbox d-block"></div>
						<button
							className="btn btn-style-1 btn-primary"
							type="button"
							data-toast=""
							data-toast-position="topRight"
							data-toast-type="success"
							data-toast-icon="fe-icon-check-circle"
							data-toast-title="Success!"
							data-toast-message="Your profile updated successfuly."
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
