import React from "react";
import RequestList from "./RequestList";

import UserSettings from "./UserSettings";
import RequestForm from "./RequestForm";
import { HashRouter, Switch, Route } from "react-router-dom";
import { withRouter } from "react-router-dom";
import OtherUserProfile from "./OtherUserProfile";
import Statistics from "./Statistics";
import FilterRequestList from "./FilterRequestList";
import RequestComponent from "./RequestComponent";
import UserHandlerRequests from "./UserHandlerRequests";

function CentarKomponenta() {
	return (
		<div
			id="center"
			className={
				/* history.location.pathname === "/newRequest" */ true
					? "center centerOverflow hiddenScroll"
					: "center centerOverflow"
			}
		>
			<HashRouter>
				<Switch>
					<Route path="/newRequest">
						<RequestForm />
					</Route>

					<Route path="/settings">
						<UserSettings />
					</Route>

					<Route path="/requests">
						<FilterRequestList />
					</Route>

					<Route path="/otherRequests">
						<UserHandlerRequests />
					</Route>

					<Route path="/myRequests">
						<RequestList
							username={
								sessionStorage.getItem("currentUserUsername")
									? sessionStorage.getItem(
											"currentUserUsername"
									  )
									: ""
							}
						/>
					</Route>

					<Route path="/user/:username">
						<OtherUserProfile />
					</Route>

					<Route path="/request/:id">
						<RequestComponent />
					</Route>

					<Route path="/rating/statistics">
						<Statistics />
					</Route>
				</Switch>
			</HashRouter>
		</div>
	);
}

export default withRouter(CentarKomponenta);
