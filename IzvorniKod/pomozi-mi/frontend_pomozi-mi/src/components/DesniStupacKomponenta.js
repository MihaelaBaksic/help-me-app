import React from "react";

//za Dev 8080, production 8080 tj. `${process.env.PUBLIC_URL}`
const logOutUrl = "http://localhost:8080/logout";
//const logOutUrl = `${process.env.PUBLIC_URL}/logout`;

function DesniStupacKomponenta(props) {
	async function logout(e) {
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

		await fetch(logOutUrl, options).then((response) => {
			if (response.status === 204) {
				console.log("Uspješan logout");
				props.setLogInFalse();
			} else {
				console.log("Neuspješan logout");
			}
		});
	}

	return (
		<div id="desniStupac" className="desniStupac">
			<div className="desniStupacContentHolder">
				<div
					type="button"
					className="btn btn-outline-secondary logOutButton"
					onClick={logout}
				>
					<div>Logout</div>
					<div>
						<svg
							viewBox="0 0 16 16"
							className="bi bi-door-open logOutIcon"
							fill="currentColor"
						>
							<path
								fillRule="evenodd"
								d="M1 15.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5zM11.5 2H11V1h.5A1.5 1.5 0 0 1 13 2.5V15h-1V2.5a.5.5 0 0 0-.5-.5z"
							/>
							<path
								fillRule="evenodd"
								d="M10.828.122A.5.5 0 0 1 11 .5V15h-1V1.077l-6 .857V15H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117z"
							/>
							<path d="M8 9c0 .552.224 1 .5 1s.5-.448.5-1-.224-1-.5-1-.5.448-.5 1z" />
						</svg>
					</div>
				</div>
			</div>
		</div>
	);
}

export default DesniStupacKomponenta;
