import React, { useState, useEffect } from "react";

function GetCurrentUserComponent(props) {
	const [podaci, setPodaci] = useState([]);

	useEffect(() => {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " +
				btoa(
					unescape(
						encodeURIComponent(
							props.username + ":" + props.password
						)
					)
				)
		);
		const options = {
			method: "GET",
			headers: myHeaders,
			redirect: "follow",
		};

		console.log(sessionStorage.getItem("isLogedIn"));
		console.log(sessionStorage.getItem("username"));
		console.log(sessionStorage.getItem("password"));

		fetch("http://localhost:8080/user/getCurrentUser", options)
			.then((response) => response.text())
			.then((result) => setPodaci(result))
			.catch((error) => console.log("error", error));
	});

	return <div>{podaci}</div>;
}

export default GetCurrentUserComponent;
