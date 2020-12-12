import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Label, Container, Button, Card, Icon, CardContent } from 'semantic-ui-react'

const baseUrl = `${process.env.PUBLIC_URL}`;

function RequestComponent(props) {
   const { id } = useParams();
	const [podaciReq, setPodaciReq] = useState([]);
	const [podaciUser, setPodaciUser] = useState([]);

	let description = [
		'Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.',
		'At vero eos et accusam et justo duo dolores et ea rebum.',
		'Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.',
	 ].join(' ')

	let name = 'Mateo Stanić | @mateostanic69 | '
	let vrijeme = '22:00:00'
	let adresaZah = 'VIRTUELNI'
	let colorBut1 = 'green'
	let colorBut2 = 'red'

	//Ovo je za fetch requesta
   useEffect(() => {
		var myHeaders = new Headers();
		const options = {
			method: "GET",
			redirect: "follow",
		};

		fetch(baseUrl + `/requests/${id}`, options)
			.then((response) => response.text())
			.then((result) => setPodaciReq(JSON.parse(result)))
			.catch((error) => console.log( "error", error));

		//console.log(JSON.stringify(podaciReq));
	}, []);

	//Ovo je trenutno redudantno ali se hvata trenutni user
	useEffect(() => {
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		const options = {
			method: "GET",
			headers: myHeaders,
			redirect: "follow",
		};

		fetch(baseUrl + "user/getCurrentUser", options)
			.then((response) => response.text())
			.then((result) => setPodaciUser(JSON.parse(result)))
			.catch((error) => console.log("error", error));

		//console.log(JSON.stringify(podaciUser));
	}, []);

	//Vršiti provjeru te na osnovu njih dodijeliti imena 
	//	name = podaciReq.user.fristName + ' ' + podaciReq.user.lastName + ' | ' + podaciReq.user.username + ' | '
	//	vrijeme = podaciReq.date
	//	adresaZah = podaciReq == null ? 'Virtualni' : podaciReq.adresa
	// if(podaciReq.username == podaciUser.username){
	//	colorBut1 = 'green'
	//	colorBut2 = 'red'
	// }
	// else{

	// }

   return (
		<Container textAlign='justified' color='blue'>
			<Card color = 'red' fluid>
				<Card.Content extra>
					<Icon name='user' size='big'/>{name}
					<Label color='orange'>
      			  Vrijeme trajanja = {vrijeme}
     				</Label>
					<Label color='orange'>
      			  Adresa = {adresaZah}
     				</Label>
				</Card.Content>
				<Card.Content header='Opis:' description={description} />
				<Card.Content extra>
			<div className='ui two buttons'>
				<Button color ={colorBut1} size='big'>
					Zahtjev obrađen
				</Button>
				<Button color={colorBut2} size='big'>
					Odustani od zahtjeva
				</Button>
			</div>
			</Card.Content>
			</Card>
		</Container>
	)
}

export default RequestComponent
