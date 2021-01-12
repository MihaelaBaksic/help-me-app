import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import MapComponent from "./MapComponent";
import L from "leaflet";
import { useForm } from "react-hook-form";

function AdressChangeCompoment(props) {
    let history = useHistory();
    const { register, handleSubmit, errors } = useForm();
    const [geoLocation, setGeoLocation] = useState(
		L.latLng(45.800623, 15.971131)
	);
    
    async function onSubmit(values, e) {
		e.preventDefault();

		console.log("fetchanje");
	}
    return (
        <div className="container">
            <form
            id="adress"
            name="adress"
            className="forma"
            onSubmit={handleSubmit(onSubmit)}
            onKeyPress={(e) => {
                e.key === "Enter" && e.preventDefault();
            }}
        >
            <h3>Unesite novu lokaciju djelovanja:</h3>
            <MapComponent setGeoLocation={setGeoLocation}/>
            <button
                    type="submit"
                    className="btn btn-primary"
                    form="adress"
                >
                    Promijeni adresu
                </button>
            </form>
        </div>
            
    );
}
export default AdressChangeCompoment;
