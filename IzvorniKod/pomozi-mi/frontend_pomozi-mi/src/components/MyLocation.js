import React, { useState, useRef, useMemo } from "react";
import { Marker } from "react-leaflet";

const center = [45.784846, 15.947278];
function MyLocation() {
	/* const [draggable, setDraggable] = useState(true); */
	const [position, setPosition] = useState(center);
	const markerRef = useRef(null);
	const eventHandlers = useMemo(
		() => ({
			dragend() {
				const marker = markerRef.current;
				if (marker != null) {
					setPosition(marker.getLatLng());
					/* console.log(marker.getLatLng()); */
				}
			},
		}),
		[]
	); /* 
	const toggleDraggable = useCallback(() => {
		setDraggable((d) => !d);
	}, []); */

	return (
		<Marker
			draggable={/* draggable */ true}
			eventHandlers={eventHandlers}
			position={position}
			ref={markerRef}
		>
			{/* <Popup minWidth={90}>
				<span onClick={toggleDraggable}>
					{draggable
						? "Marker is draggable"
						: "Click here to make marker draggable"}
				</span>
			</Popup> */}
		</Marker>
	);
}

export default MyLocation;
