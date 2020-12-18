import React, {
	useState,
	useEffect,
	useRef,
	useMemo,
	useCallback,
} from "react";
import { useForm } from "react-hook-form";
import { MapContainer, TileLayer, Marker, Popup } from "react-leaflet";

const center = [45.784846, 15.947278];
const zoomLevel = 14;

function MapSearch() {
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
			.then((result) => console.log(JSON.parse(result)))
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
function DisplayPosition({ map }) {
	const [position, setPosition] = useState(map.getCenter());

	const onClick = useCallback(() => {
		map.setView(center, zoomLevel);
	}, [map]);

	const onMove = useCallback(() => {
		setPosition(map.getCenter());
	}, [map]);

	useEffect(() => {
		map.on("move", onMove);
		return () => {
			map.off("move", onMove);
		};
	}, [map, onMove]);

	return (
		<p>
			latitude: {position.lat.toFixed(4)}, longitude:{" "}
			{position.lng.toFixed(4)} <button onClick={onClick}>reset</button>
		</p>
	);
}

function MyLocation({ map }) {
	const [markerPos, setMarkerPos] = useState(center);

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

	return (
		<div>
			<Marker
				draggable={/* draggable */ true}
				eventHandlers={eventHandlers}
				position={markerPos}
				ref={markerRef}
			></Marker>
		</div>
	);
}
function MapComponent() {
	const [map, setMap] = useState(null);

	const displayMap = useMemo(
		() => (
			<MapContainer
				center={center}
				zoom={zoomLevel}
				scrollWheelZoom={true}
				whenCreated={setMap}
			>
				<TileLayer
					attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
					url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
				/>

				<MyLocation map={map} />
				<MapSearch />
			</MapContainer>
		),
		[]
	);
	return (
		<div>
			{map ? <DisplayPosition map={map} /> : null}
			{displayMap}
		</div>
	);
}

export default MapComponent;
