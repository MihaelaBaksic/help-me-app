import React from "react";
import CentarKomponenta from "./CentarKomponenta";
import LijeviStupacKomponenta from "./LijeviStupacKomponenta";
import DesniStupacKomponenta from "./DesniStupacKomponenta";

function LogedInUserComponent(props) {
	function toggleRightExtender() {
		if (window.innerWidth < "1400" /*  mjerilo u pikselima*/) {
			if (
				document.getElementById("desniStupac").style.width === "250px"
			) {
				document.getElementById("desniStupac").style.width = "0";
				//document.getElementById("pageBody").style.marginRight = "0";
				//document.body.style.backgroundColor = "white";
			} else {
				document.getElementById("desniStupac").style.width = "250px";
				//document.getElementById("pageBody").style.marginRight = "250px";
				//document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
			}
		}
	}

	function toggleLeftExtender() {
		if (window.innerWidth < "800" /*  mjerilo u pikselima*/) {
			if (
				document.getElementById("lijeviStupac").style.width === "250px"
			) {
				document.getElementById("lijeviStupac").style.width = "0";
			}
			//document.getElementById("pageBody").style.marginRight = "0";
			//document.body.style.backgroundColor = "white";
			else {
				document.getElementById("lijeviStupac").style.width = "250px";
				//document.getElementById("pageBody").style.marginRight = "250px";
				//document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
			}
		}
	}

	return (
		<div id="main" className="main">
			<div className="topHeader">
				<button
					type="button"
					className="leftExtender btn btn-warning"
					onClick={toggleLeftExtender}
				>
					<svg width="1em" height="1em" className="bi bi-file-person">
						<path
							fill-rule="evenodd"
							d="M12 1H4a1 1 0 0 0-1 1v10.755S4 11 8 11s5 1.755 5 1.755V2a1 1 0 0 0-1-1zM4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4z"
						/>
						<path
							fill-rule="evenodd"
							d="M8 10a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"
						/>
					</svg>
				</button>

				{/* <form className="searchBar">
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
				</form> */}
				<form class="form-inline">
					<input
						class="form-control mr-sm-2"
						type="search"
						placeholder="Pretraži.."
						aria-label="Search"
					/>
					<button class="btn btn-light my-2 my-sm-0" type="submit">
						<svg width="1em" height="1em" class="bi bi-search">
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
				</form>

				<button
					type="button"
					className="rightExtender btn btn-warning"
					onClick={toggleRightExtender}
				>
					<svg width="1em" height="1em" class="bi bi-tools">
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

			<div id="pageBody" className="pageBody">
				<LijeviStupacKomponenta />
				<CentarKomponenta />
				<DesniStupacKomponenta setLogInFalse={props.setLogInFalse} />
			</div>
		</div>
	);
}

export default LogedInUserComponent;
