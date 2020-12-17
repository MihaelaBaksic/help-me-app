import React from "react";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";
import MyLocation from "./MyLocation";

function MapComponent() {
	return (
		<MapContainer
			center={[45.784846, 15.947278]}
			zoom={15}
			scrollWheelZoom={true}
		>
			<TileLayer
				attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
				url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
			/>

			<MyLocation />
		</MapContainer>
	);
}

export default MapComponent;
