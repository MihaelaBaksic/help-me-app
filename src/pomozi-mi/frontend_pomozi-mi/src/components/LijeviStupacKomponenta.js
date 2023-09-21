import React from "react";

import ViewProfileComponent from "./ViewProfileComponent";

function LijeviStupacKomponenta(props) {
	return (
		<div id="lijeviStupac" className="lijeviStupac">
			<ViewProfileComponent changeState={props.changeState} />
		</div>
	);
}

export default LijeviStupacKomponenta;
