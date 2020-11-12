import React, { useState, useEffect } from "react";

const baseUrl = `${process.env.PUBLIC_URL}`;

function GetCurrentUserComponent(props) {
	const [podaci, setPodaci] = useState([]);

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

		console.log(sessionStorage.getItem("isLogedIn"));
		console.log(sessionStorage.getItem("basicAuthToken"));

		fetch(baseUrl + "user/getCurrentUser", options)
			.then((response) => response.text())
			.then((result) => setPodaci(JSON.parse(result)))
			.catch((error) => console.log("error", error));

		console.log(JSON.stringify(podaci));
	}, []);

	return (
		<div className="userData">
			Dobrodošao ulogirani korisniče {podaci.firstName} {podaci.lastName}
			<br />
			Vaša email adresa je: {podaci.email}
			<br />
			Vaše korisničko ime je: {podaci.username}
			<br />
			{podaci.administrator == "true"
				? "Ti si administrator"
				: "Nisi administrator"}
		</div>
	);
}

export default GetCurrentUserComponent;
