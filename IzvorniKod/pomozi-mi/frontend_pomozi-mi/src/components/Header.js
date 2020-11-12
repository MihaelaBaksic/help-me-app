import React from "react";
import GetCurrentUserComponent from "./GetCurrentUserComponent";

//za Dev 8080, production 8080 tj. `${process.env.PUBLIC_URL}`
//const logOutUrl = "http://localhost:8080/logout";
const loginUrl = `${process.env.PUBLIC_URL}/logout`;

function Header(props) {
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
				props.setLogInFalseHandler();
			} else {
				console.log("Neuspješan logout");
			}
		});
	}

	function toggleSideMenu() {
		if (document.getElementById("mySidenav").style.width === "250px") {
			document.getElementById("mySidenav").style.width = "0";
			document.getElementById("main").style.marginRight = "0";
			//document.body.style.backgroundColor = "white";
		} else {
			document.getElementById("mySidenav").style.width = "250px";
			document.getElementById("main").style.marginRight = "250px";
			//document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
		}
	}

	return (
		<div id="main" className="main">
			<div className="header">
				<div id="mySidenav" className="sidenav">
					<a href="/">About</a>
					<div
						type="button"
						class="btn btn-outline-secondary logOutButton"
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
									fill-rule="evenodd"
									d="M1 15.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5zM11.5 2H11V1h.5A1.5 1.5 0 0 1 13 2.5V15h-1V2.5a.5.5 0 0 0-.5-.5z"
								/>
								<path
									fill-rule="evenodd"
									d="M10.828.122A.5.5 0 0 1 11 .5V15h-1V1.077l-6 .857V15H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117z"
								/>
								<path d="M8 9c0 .552.224 1 .5 1s.5-.448.5-1-.224-1-.5-1-.5.448-.5 1z" />
							</svg>
						</div>
					</div>
				</div>
				<button
					type="button"
					className="btn btn-outline-dark menuExtender"
				>
					<svg
						viewBox="0 0 16 16"
						class="bi bi-list "
						fill="currentColor"
						xmlns="http://www.w3.org/2000/svg"
					>
						<path
							fill-rule="evenodd"
							d="M2.5 11.5A.5.5 0 0 1 3 11h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4A.5.5 0 0 1 3 7h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4A.5.5 0 0 1 3 3h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"
						/>
					</svg>
				</button>

				<form className="searchBar">
					<div>
						<input
							name="password"
							type="password"
							className="form-control"
							placeholder="Pretražite.."
						></input>
					</div>
					<div>
						<button type="button" class="btn btn-light searchBtn">
							<svg
								viewBox="0 0 16 16"
								class="bi bi-search"
								fill="currentColor"
								xmlns="http://www.w3.org/2000/svg"
							>
								<path
									fill-rule="evenodd"
									d="M10.442 10.442a1 1 0 0 1 1.415 0l3.85 3.85a1 1 0 0 1-1.414 1.415l-3.85-3.85a1 1 0 0 1 0-1.415z"
								/>
								<path
									fill-rule="evenodd"
									d="M6.5 12a5.5 5.5 0 1 0 0-11 5.5 5.5 0 0 0 0 11zM13 6.5a6.5 6.5 0 1 1-13 0 6.5 6.5 0 0 1 13 0z"
								/>
							</svg>
						</button>
					</div>
				</form>

				<button
					type="button"
					className="btn btn-outline-dark menuExtender"
					onClick={toggleSideMenu}
				>
					<svg
						viewBox="0 0 16 16"
						class="bi bi-tools"
						fill="currentColor"
						xmlns="http://www.w3.org/2000/svg"
					>
						<path
							fill-rule="evenodd"
							d="M0 1l1-1 3.081 2.2a1 1 0 0 1 .419.815v.07a1 1 0 0 0 .293.708L10.5 9.5l.914-.305a1 1 0 0 1 1.023.242l3.356 3.356a1 1 0 0 1 0 1.414l-1.586 1.586a1 1 0 0 1-1.414 0l-3.356-3.356a1 1 0 0 1-.242-1.023L9.5 10.5 3.793 4.793a1 1 0 0 0-.707-.293h-.071a1 1 0 0 1-.814-.419L0 1zm11.354 9.646a.5.5 0 0 0-.708.708l3 3a.5.5 0 0 0 .708-.708l-3-3z"
						/>
						<path
							fill-rule="evenodd"
							d="M15.898 2.223a3.003 3.003 0 0 1-3.679 3.674L5.878 12.15a3 3 0 1 1-2.027-2.027l6.252-6.341A3 3 0 0 1 13.778.1l-2.142 2.142L12 4l1.757.364 2.141-2.141zm-13.37 9.019L3.001 11l.471.242.529.026.287.445.445.287.026.529L5 13l-.242.471-.026.529-.445.287-.287.445-.529.026L3 15l-.471-.242L2 14.732l-.287-.445L1.268 14l-.026-.529L1 13l.242-.471.026-.529.445-.287.287-.445.529-.026z"
						/>
					</svg>
				</button>
			</div>
			<div className="pageBody">
				{/* <SideBarComponent /> */}
				<GetCurrentUserComponent />
			</div>
		</div>
	);
}

export default Header;
