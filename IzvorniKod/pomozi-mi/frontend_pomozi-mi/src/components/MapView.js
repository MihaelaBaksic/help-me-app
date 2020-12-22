import React, { Component } from 'react';
import { MapContainer as LeafletMap , TileLayer } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import data from '../assets/data';
import Markers from './VenueMarkers';

class MapView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentLocation: { 
        lat: 45.8150,
        lng: 15.9819 
      },
      zoom: 14,
    }
  }

  render() {
    const { currentLocation, zoom } = this.state;

    return (
      <LeafletMap center={currentLocation} zoom={zoom}>
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution="&copy; <a href=&quot;http://osm.org/copyright&quot;>OpenStreetMap</a> contributors"
        />

        <Markers venues={data.venues}/>
      </LeafletMap>
    );
  }
}

export default MapView;