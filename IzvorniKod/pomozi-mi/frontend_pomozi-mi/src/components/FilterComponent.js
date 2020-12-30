import React from "react";
import { CustomInput, Form, FormGroup, Label, Input } from "reactstrap";
import { Divider, Button, Checkbox } from "semantic-ui-react";
import { useForm } from "react-hook-form";
import { useEffect, useState } from "react";

function FilterComponent(props) {
	const { handleSubmit, register, errors, watch } = useForm({});
	const [radius, setRadius] = useState(5);
	const [sort, setSort] = useState(true);
	const [virtual, setVirtual] = useState(false);

	function onSubmit(values, e) {
		console.log("Body u FilterComponent: ");

		let filter = {
			radius: radius,
			virtual: virtual,
			order: sort ? "ATOZ" : "ZTOA",
		};
		console.log(JSON.stringify(filter));

		props.setFilterBody(JSON.stringify(filter));
	}

	function handleRadius(e) {
		//console.log(e.target.value);
		setRadius(e.target.value);
	}

	function handleSortAZ(e) {
		//console.log(true);
		setSort(true);
	}

	function handleSortZA(e) {
		//console.log(false);
		setSort(false);
	}

	function handleVirtual(e) {
		//console.log(e.target.checked);
		setVirtual(e.target.checked);
	}

	return (
		<div>
			<Form inline onSubmit={handleSubmit(onSubmit)}>
				<FormGroup>
					<Label for="radius">Radius:</Label>
					<Input
						type="text"
						name="address"
						id="radius"
						placeholder="5 km"
						onChange={handleRadius}
					/>
				</FormGroup>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<FormGroup>
					<Label for="radius">Sortiranje:</Label>
					<CustomInput
						type="radio"
						id="a_z"
						name="customRadio"
						label="A - Z"
						onChange={handleSortAZ}
					/>
					<CustomInput
						type="radio"
						id="z_a"
						name="customRadio"
						label="Z - A"
						onChange={handleSortZA}
					/>
				</FormGroup>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<FormGroup check>
					<Input
						type="checkbox"
						name="check"
						id="exampleCheck"
						onClick={handleVirtual}
					/>
					<Label for="exampleCheck" check>
						Virtualni
					</Label>
				</FormGroup>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<div>
					<Button type="submit" size = "small">
						Filtriraj
					</Button>
				</div>
			</Form>
		</div>
	);
}

export default FilterComponent;
