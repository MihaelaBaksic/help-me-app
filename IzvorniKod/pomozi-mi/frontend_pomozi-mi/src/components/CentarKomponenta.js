import React from "react";
import RequestList from "./RequestList";

import UserSettings from "./UserSettings";
import DeleteComponent from "./DeleteComponent";
import RequestForm from "./RequestForm";
import {
	HashRouter,
	Switch,
	Route,
	BrowserRouter,
	useRouteMatch,
	Router,
} from "react-router-dom";
import { Browser } from "leaflet";

function CentarKomponenta(props) {
	let { path, url } = useRouteMatch();
	console.log(path);
	return (
		<div id="center" className="center">
			<HashRouter>
				<Switch>
					<Route path="/newRequest" render={() => <RequestForm />} />
					<Route path="/settings" render={() => <UserSettings />} />
					<Route path="requests" render={() => <RequestList />} />
					<Route
						path="/myRequests"
						render={() => (
							<RequestList
								username={sessionStorage.getItem(
									"currentUserUsername"
								)}
							/>
						)}
					/>
				</Switch>
			</HashRouter>
			{/* {props.show === "newRequest" && <RequestForm />}
			{props.show === "settings" && <UserSettings />}
			{props.show === "requests" && <RequestList />}
			{props.show === "myRequests" && (
				<RequestList
					username={sessionStorage.getItem("currentUserUsername")}
				/>
			)}
			{props.show === "delete" && <DeleteComponent />} */}
		</div>
	);
}

export default CentarKomponenta;
