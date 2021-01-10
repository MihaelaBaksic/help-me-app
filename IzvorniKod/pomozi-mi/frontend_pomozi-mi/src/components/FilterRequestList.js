import React from "react";
import { Divider } from "semantic-ui-react";
import { useEffect, useState } from "react";
import FilterComponent from "./FilterComponent";
import RequestList from "./RequestList";

//const baseUrl = `${process.env.PUBLIC_URL}`;
const baseUrl = "http://localhost:8080";
function FilterRequestList(props) {
	let filter = {
		radius: 5,
		virtual: false,
		order: "STANDARD",
	};
	const [requestsF, setRequestsF] = useState("");
	const [filterBody, setFilterBody] = useState(JSON.stringify(filter));

	useEffect(() => {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		myHeaders.append("Content-Type", "application/json");

		const options = {
			method: "POST",
			headers: myHeaders,
			redirect: "follow",
			body: filterBody,
		};

		fetch(
			props.username
				? baseUrl + "/user/authoredRequests/" + props.username
				: baseUrl + "/requests/all",
			options
		)
			.then((response) => response.text())
			.then((result) => {
				if (JSON.parse(result)._embedded) {
					setRequestsF(JSON.parse(result)._embedded.requestDTOList);
				} else {
					setRequestsF("");
				}
			})
			.catch((error) => {
				/* console.log("error: ", error, "LISTA VJEROJATNO PRAZNA"); */
				setRequestsF("");
			});
	}, [filterBody, props.username]);

	if (requestsF) {
		/* console.log("Body = ");
		console.log(filterBody); */
		return (
			<div className="card centerContent">
				<div role="list">
					<FilterComponent
						setFilterBody={(body) => setFilterBody(body)}
					/>
				</div>

				<Divider hidden fitted />
				{requestsF ? <RequestList listaZahtjeva={requestsF} /> : null}
			</div>
		);
	} else {
		return (
			<div className="card centerContent">
				<FilterComponent
					setFilterBody={(body) => setFilterBody(body)}
				/>
				<h1>Lista zahtjeva je prazna</h1>
			</div>
		);
	}
}

export default FilterRequestList;
