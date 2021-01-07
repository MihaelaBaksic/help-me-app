import React, { Component } from "react";
import CentarKomponenta from "./CentarKomponenta";
import LijeviStupacKomponenta from "./LijeviStupacKomponenta";
import DesniStupacKomponenta from "./DesniStupacKomponenta";

class LogedInUserComponent extends Component {
	/* constructor() {
		super();
		this.state = {
			showing: "myRequests",
		};
		this.changeState = this.changeState.bind(this);
	}
	changeState(s) {
		this.setState({
			showing: s,
		});
	} */
	toggleRightExtender() {
		let desniStpc = document.getElementById("desniStupac");
		let lijeviStpc = document.getElementById("lijeviStupac");
		let tamniOverlay = document.getElementById("darkOverlay");
		if (window.innerWidth < "1500" /*px*/) {
			if (desniStpc.style.width === "250px") {
				desniStpc.style.width = "0px";
				if (lijeviStpc.style.width !== "250px") {
					tamniOverlay.style.visibility = "hidden";
					tamniOverlay.style.zIndex = "-1";
					tamniOverlay.style.backgroundColor = "rgba(0, 0, 0, 0)";
				}
			} else {
				desniStpc.style.width = "250px";
				tamniOverlay.style.visibility = "visible";
				tamniOverlay.style.zIndex = "2";
				tamniOverlay.style.left = 0;
				tamniOverlay.style.backgroundColor = "rgba(0, 0, 0, 0.75)";

				if (window.innerWidth < "1100" /*px*/) {
					if (lijeviStpc.style.width === "250px") {
						lijeviStpc.style.width = "0";
					}
				}
			}
		}
	}

	toggleLeftExtender() {
		let tamniOverlay = document.getElementById("darkOverlay");
		let desniStpc = document.getElementById("desniStupac");
		let lijeviStpc = document.getElementById("lijeviStupac");
		if (window.innerWidth < "1100" /*px*/) {
			if (lijeviStpc.style.width === "250px") {
				lijeviStpc.style.width = "0";
				if (desniStpc.style.width !== "250px") {
					tamniOverlay.style.visibility = "hidden";
					tamniOverlay.style.zIndex = "-1";
					tamniOverlay.style.backgroundColor = "rgba(0, 0, 0, 0)";
				}
			} else {
				lijeviStpc.style.width = "250px";
				tamniOverlay.style.visibility = "visible";
				tamniOverlay.style.zIndex = "2";
				tamniOverlay.style.right = "0";
				tamniOverlay.style.backgroundColor = "rgba(0, 0, 0, 0.75)";

				if (window.innerWidth < "1100" /*px*/) {
					if (desniStpc.style.width === "250px") {
						desniStpc.style.width = "0";
					}
				}
			}
		}
	}
	render() {
		return (
			<div id="main" className="main">
				<div className="topHeader">
					<button
						type="button"
						className="leftExtender btn btn-light"
						onClick={this.toggleLeftExtender}
					>
						<img
							id="leftExtenderIcon"
							src="https://image.flaticon.com/icons/png/512/126/126486.png"
							alt="FreePik Icon User"
						/>
					</button>

					<form className="form-inline">
						<input
							className="form-control mr-sm-2"
							type="search"
							placeholder="Pretraži.."
							aria-label="Search"
						/>
						<button
							className="btn btn-light"
							type="button"
							onClick={() => {}}
						>
							Search
						</button>
					</form>

					<button
						type="button"
						className="rightExtender btn btn-light"
						onClick={this.toggleRightExtender}
					>
						<img
							id="rightExtenderIcon"
							src="https://image.flaticon.com/icons/png/512/126/126391.png"
							alt="FreePik Icon User"
						/>
					</button>
				</div>

				<div id="pageBody" className="pageBody">
					<LijeviStupacKomponenta /* changeState={this.changeState}  */
					/>
					<CentarKomponenta /* show={this.state.showing}  */ />
					<DesniStupacKomponenta
						setLogInFalse={this.props.setLogInFalse}
					/>
					<div id="darkOverlay"></div>
				</div>
			</div>
		);
	}
}

export default LogedInUserComponent;
