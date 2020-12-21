import React, {
	useState,
	useEffect,
	useRef,
	useMemo,
	useCallback,
} from "react";
import { useForm } from "react-hook-form";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";

const initialCenter = [45.784846, 15.947278];
let currentCenter = [45.784846, 15.947278];
const zoomLevel = 14;

function MapSearch(props) {
	const { handleSubmit, register, errors } = useForm({});
	async function onSubmit(values, e) {
		e.preventDefault();

		const options = {
			method: "GET",
			redirect: "follow",
		};
		let findUrl =
			"https://nominatim.openstreetmap.org/?q=" +
			values.q +
			"&format=json";

		await fetch(findUrl, options)
			.then((response) => response.text())
			.then((result) => {
				console.log(
					"lat: " +
						JSON.parse(result)[0].lat +
						" lon: " +
						JSON.parse(result)[0].lon
				);
				props.map.setView(
					[JSON.parse(result)[0].lat, JSON.parse(result)[0].lon],
					zoomLevel
				);
				console.log("jos jedna");
			})
			.catch((error) => console.log("error", error));
	}
	return (
		<form onSubmit={handleSubmit(onSubmit)}>
			<div className="form-group">
				<input
					id="mapSearchBar"
					className="form-control"
					type="search"
					name="q"
					placeholder="Pretraži.."
					ref={register()}
				/>
			</div>
		</form>
	);
}
function DisplayPosition(props) {
	const [position, setPosition] = useState(props.map.getCenter());

	const onClick = useCallback(() => {
		props.map.setView(initialCenter, zoomLevel);
	}, [props.map]);

	const onMove = useCallback(() => {
		setPosition(props.map.getCenter());
		currentCenter = props.map.getCenter();
	}, [props.map]);

	useEffect(() => {
		props.map.on("move", onMove);
		return () => {
			props.map.off("move", onMove);
		};
	}, [props.map, onMove]);

	return (
		<p>
			latitude: {position.lat.toFixed(4)}, longitude:{" "}
			{position.lng.toFixed(4)} <button onClick={onClick}>reset</button>
		</p>
	);
}

function MapComponent() {
	const [map, setMap] = useState(null);
	const [markerPos, setMarkerPos] = useState(initialCenter);

	function postaviMarka() {
		console.log("bruh");
		setMarkerPos(currentCenter);
		console.log(markerPos);
	}

	const markerRef = useRef(null);
	const eventHandlers = useMemo(
		() => ({
			dragend() {
				const marker = markerRef.current;
				if (marker != null) {
					setMarkerPos(marker.getLatLng());
					console.log(marker.getLatLng());
				}
			},
		}),
		[]
	);
	const displayMap = useMemo(
		() => (
			<MapContainer
				center={initialCenter}
				zoom={zoomLevel}
				scrollWheelZoom={true}
				whenCreated={setMap}
			>
				<TileLayer
					attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
					url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
				/>

				<Marker
					map={map}
					draggable={/* draggable */ true}
					eventHandlers={eventHandlers}
					position={markerPos}
					ref={markerRef}
				></Marker>
			</MapContainer>
		),
		[markerPos]
	);
	return (
		<div>
			{map ? <button onClick={postaviMarka}>CenterMarker</button> : null}
			{map ? <MapSearch map={map} /> : null}
			{map ? <DisplayPosition map={map} /> : null}
			{displayMap}
		</div>
	);
}

export default MapComponent;
