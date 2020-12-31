import React, { useEffect, useState } from "react";
import RequestList from "./RequestList";

import UserSettings from "./UserSettings";
import RequestForm from "./RequestForm";
import { HashRouter, Switch, Route } from "react-router-dom";
import { useHistory, withRouter } from "react-router-dom";
import OtherUserProfile from "./OtherUserProfile";
import Statistics from "./Statistics";

function CentarKomponenta(props) {
	let history = useHistory();

	return (
		<div
			id="center"
			className={
				history.location.pathname === "/newRequest"
					? "center centerOverflow hiddenScroll"
					: "center centerOverflow"
			}
		>
			<HashRouter>
				<Switch>
					<Route path="/newRequest" component={RequestForm} />
					<Route path="/settings" component={UserSettings} />
					<Route path="/requests" component={RequestList} />
					<Route
						path="/myRequests"
						render={() => (
							<RequestList
								username={
									sessionStorage.getItem(
										"currentUserUsername"
									)
										? sessionStorage.getItem(
												"currentUserUsername"
										  )
										: ""
								}
							/>
						)}
					/>
					<Route
						path="/user/:username"
						render={() => <OtherUserProfile />}
					/>
					<Route path="/rating/statistics" component={Statistics} />
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

export default withRouter(CentarKomponenta);
