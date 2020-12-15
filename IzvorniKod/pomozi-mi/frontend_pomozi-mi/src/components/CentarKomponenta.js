import React from "react";
import GetCurrentUserComponent from "./GetCurrentUserComponent";
import RequestList from "./RequestList";

import UserSettings from "./UserSettings";
import DeleteComponent from "./DeleteComponent";

function CentarKomponenta(props) {
	/*const requests = [
      {autor: 'Baja Bajić', naziv: 'Dođi mi na party',
       adresa: "Na krovu konzuma", komentar: "Tamo bude sviral Cetinski i bit će brutala buraz moraš doć e"}
      ,{autor: 'Maja Bajamić', naziv: 'Gledaj me na Farmi', 
      adresa: "Babin dol", komentar: "Opet se guram na televiziji pomogni mi da budem popularna"}
      ,{autor: 'Tomislav Kralj', naziv: 'Gledaj me dok se krunim', 
      adresa: 'Trg kralja Tomislava', komentar: "Krunim se kaj hoćeš da ti još kažem..."}
];*/
	return (
		<div id="center" className="center">
			{/* <RequestList /> */}
			{props.show === "settings" && <UserSettings />}
			{props.show === "myRequests" && <RequestList />}
			{props.show === "delete" && <DeleteComponent />}
		</div>
	);
}

export default CentarKomponenta;
