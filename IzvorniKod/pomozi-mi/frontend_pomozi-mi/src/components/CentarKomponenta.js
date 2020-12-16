import React from "react";
import RequestList from "./RequestList";

import UserSettings from "./UserSettings";
import DeleteComponent from "./DeleteComponent";

function CentarKomponenta(props) {
	return (
		<div id="center" className="center">
			{props.show === "settings" && <UserSettings />}
			{props.show === "myRequests" && <RequestList />}
			{props.show === "delete" && <DeleteComponent />}
		</div>
	);
}

export default CentarKomponenta;
