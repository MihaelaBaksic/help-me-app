import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import RequestList from "./RequestList";
import CommentComponent from "./CommentComponent";
import CommentFormComponent from "./CommentFormComponent";

const baseUrl = `${process.env.PUBLIC_URL}`;
//const baseUrl = "http://localhost:8080";

function OtherUserProfile(props) {
	let { username } = useParams();
	let [userInfo, setUserInfo] = useState("");
	let [authoredRequests, setAuthoredRequests] = useState("");
	let [handlerRequests, setHandlerRequests] = useState("");
	let [ratings, setRatings] = useState("");

	/*  */
	const [refreshCenter, setRefreshCenter] = useState("");

	function reRenderaj() {
		if (refreshCenter === "") {
			setRefreshCenter("1");
		} else {
			setRefreshCenter("");
		}
	}
	/*  */

	function addAsAdmin() {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		const options = {
			method: "POST",
			headers: myHeaders,
			redirect: "follow",
		};

		fetch(baseUrl + "/user/setAdmin/" + username, options)
			.then((response) => response.text())
			.then((result) => {
				/* console.log(JSON.parse(result)); */
				setUserInfo(JSON.parse(result));
			})
			.catch((error) => {
				console.log("error", error);
			});
	}
	function permaBlockUser() {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		myHeaders.append("Content-Type", "application/json");
		var raw = "true";
		const options = {
			method: "POST",
			headers: myHeaders,
			redirect: "follow",
			body: raw,
		};

		fetch(baseUrl + "/user/blockUser/" + username, options)
			.then((response) => response.text())
			.then((result) => {
				/* console.log(JSON.parse(result)); */
				setUserInfo(JSON.parse(result));
			})
			.catch((error) => {
				console.log("error", error);
			});
	}
	function blockUser() {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		myHeaders.append("Content-Type", "application/json");
		var raw = "false";
		const options = {
			method: "POST",
			headers: myHeaders,
			redirect: "follow",
			body: raw,
		};

		fetch(baseUrl + "/user/blockUser/" + username, options)
			.then((response) => response.text())
			.then((result) => {
				/* console.log(JSON.parse(result)); */
				setUserInfo(JSON.parse(result));
			})
			.catch((error) => {
				console.log("error", error);
			});
	}
	function unBlockUser() {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		const options = {
			method: "POST",
			headers: myHeaders,
			redirect: "follow",
		};

		fetch(baseUrl + "/user/unblockUser/" + username, options)
			.then((response) => response.text())
			.then((result) => {
				/* console.log(JSON.parse(result)); */
				setUserInfo(JSON.parse(result));
			})
			.catch((error) => {
				console.log("error", error);
			});
	}

	useEffect(() => {
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

		fetch(baseUrl + "/user/" + username, options)
			.then((response) => response.text())
			.then((result) => {
				/* console.log(JSON.parse(result)); */
				setUserInfo(JSON.parse(result));
			})
			.catch((error) => {
				console.log("error", error);
				setUserInfo("");
			});
	}, [username]);
	useEffect(() => {
		if (userInfo.status === "TEMPBLOCK") {
			setAuthoredRequests("");
		} else if (userInfo.status === "PERMABLOCK") {
			setAuthoredRequests("");
		} else if (userInfo.status === "NOTBLOCKED") {
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
			fetch(baseUrl + "/user/authoredRequests/" + username, options)
				.then((response) => response.text())
				.then((result) => {
					if (JSON.parse(result)._embedded) {
						setAuthoredRequests(
							JSON.parse(result)._embedded.requestDTOList
						);
					} else {
						setAuthoredRequests("");
					}
				})
				.catch((error) => {
					console.log("error", error);
					setAuthoredRequests("");
				});
			fetch(baseUrl + "/user/handlerRequests/" + username, options)
				.then((response) => response.text())
				.then((result) => {
					if (JSON.parse(result)._embedded) {
						setHandlerRequests(
							JSON.parse(result)._embedded.requestDTOList
						);
					} else {
						setHandlerRequests("");
					}
				})
				.catch((error) => {
					console.log("error", error);
					setHandlerRequests("");
				});
		}
	}, [userInfo.status, username]);

	useEffect(() => {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		myHeaders.append("Content-Type", "application/json");

		const options = {
			method: "GET",
			headers: myHeaders,
			redirect: "follow",
		};

		fetch(baseUrl + "/rating/of/" + username, options)
			.then((response) => response.text())
			.then((result) => {
				setRatings(JSON.parse(result));
				//console.log(result);
			});
	}, [username, refreshCenter]);

	useEffect(() => {
		/* console.log(ratings); */
	}, [ratings]);

	useEffect(() => {
		/* console.log(authoredRequests); */
	}, [authoredRequests]);

	useEffect(() => {
		/* console.log(handlerRequests); */
	}, [handlerRequests]);

	if (userInfo) {
		return (
			<div className="layout-content">
				<div className="container flex-grow-1 container-p-y">
					<div className="container-m-nx container-m-ny theme-bg-white mb-4">
						<div className="media col-md-10 col-lg-8 col-xl-7 py-5 mx-auto">
							<img
								src="https://bootdey.com/img/Content/avatar/avatar1.png"
								alt=""
								className="d-block ui-w-100 rounded-circle"
							/>

							<div className="media-body ml-5">
								<h4 className="font-weight-bold mb-4">
									{userInfo.firstName +
										" " +
										userInfo.lastName}
								</h4>

								{/* 	<a className="d-inline-block text-body ml-3">
									<strong>51</strong>
									<span className="text-muted">
										izvršenih zahtjeva
									</span>
								</a>
								<a className="d-inline-block text-body ml-3">
									<strong>44</strong>
									<span className="text-muted">
										postavljenih zahtjeva
									</span>
								</a> */}

								{sessionStorage.getItem("isAdmin") === "true" &&
								userInfo.administrator === false &&
								userInfo.status === "NOTBLOCKED" ? (
									<div className="mt-3">
										<div
											role="button"
											className="btn btn-success rounded-pill col-xl-6"
											onClick={addAsAdmin}
										>
											<i className="far fa-id-badge"></i>
											&nbsp; Dodaj kao admina
										</div>
									</div>
								) : null}
								{sessionStorage.getItem("isAdmin") === "true" &&
								userInfo.administrator === false &&
								userInfo.status === "NOTBLOCKED" ? (
									<div className="mt-3">
										<div
											role="button"
											className="btn btn-warning rounded-pill col-xl-6"
											onClick={blockUser}
										>
											<i className="fas fa-lock"></i>
											&nbsp; Privremeno blokiraj korisnika
										</div>
									</div>
								) : null}
								{sessionStorage.getItem("isAdmin") === "true" &&
								userInfo.administrator === false &&
								userInfo.status === "NOTBLOCKED" ? (
									<div className="mt-3">
										<div
											role="button"
											className="btn btn-danger rounded-pill col-xl-6"
											onClick={permaBlockUser}
										>
											<i className="fas fa-ban"></i>&nbsp;
											Trajno blokiraj korisnika
										</div>
									</div>
								) : null}
								{sessionStorage.getItem("isAdmin") === "true" &&
								userInfo.status === "TEMPBLOCK" ? (
									<div className="mt-3">
										<div
											role="button"
											className="btn btn-info rounded-pill col-xl-6"
											onClick={unBlockUser}
										>
											<i className="fas fa-lock-open"></i>
											&nbsp; Odblokiraj korisnika
										</div>
									</div>
								) : null}
							</div>
						</div>

						<hr className="m-0" />
					</div>

					{userInfo.status === "NOTBLOCKED" ? (
						<div>
							<div className="row">
								<div className="col">
									<div className="card mb-4">
										<div className="card-body">
											<div className="col-xl-12">
												<div className="page-header">
													<h3>
														Postavljeni zahtjevi
													</h3>
													<hr />
												</div>
												{authoredRequests ? (
													<RequestList
														listaZahtjeva={
															authoredRequests
														}
													/>
												) : null}
											</div>
										</div>
									</div>
								</div>
							</div>

							<div className="row">
								<div className="col">
									<div className="card mb-4">
										<div className="card-body">
											<div className="col-xl-12">
												<div className="page-header">
													<h3>Izvršeni zahtjevi</h3>
													<hr />
												</div>
												{handlerRequests ? (
													<RequestList
														listaZahtjeva={
															handlerRequests
														}
													/>
												) : null}
											</div>
										</div>
									</div>
								</div>
							</div>
							<div className="row">
								<div className="col-xl-8">
									<div className="card mb-4">
										<div className="card-body">
											<div className="page-header">
												<h3>Komentari</h3>
												<hr />
											</div>
											<CommentComponent
												listaKomentara={ratings}
												korIme={username}
											/>
										</div>
									</div>
								</div>
								{sessionStorage.getItem(
									"currentUserUsername"
								) !== username ? (
									<div className="col-xl-4">
										<div className="card mb-4">
											<div className="card-body">
												<div className="page-header">
													<h3>Ocijeni korisnika</h3>
													<hr />
												</div>
												<CommentFormComponent
													korIme={username}
													reRenderaj={reRenderaj}
												/>
											</div>
										</div>
									</div>
								) : null}
							</div>
						</div>
					) : (
						<div className="col-xl-8">
							<div className="card mb-4">
								<div className="card-body">
									<div className="page-header">
										<h3>KORISNIK JE BLOKIRAN!</h3>
										<hr />
									</div>
									<i className="fas fa-lock fa-10x mb-4"></i>
								</div>
							</div>
						</div>
					)}
				</div>
			</div>
		);
	} else {
		return <div>Korisnik ne postoji?</div>;
	}
}
export default OtherUserProfile;
