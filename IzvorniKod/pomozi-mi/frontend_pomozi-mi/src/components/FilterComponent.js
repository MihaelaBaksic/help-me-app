import React from "react";
import { CustomInput, Label } from "reactstrap";
import { Button, Form, Input } from "semantic-ui-react";
import { useForm } from "react-hook-form";
import { useState } from "react";

function FilterComponent(props) {
	const { handleSubmit, register, errors, watch } = useForm({});
	const [radius, setRadius] = useState(5);
	const [sort, setSort] = useState(true);
	const [virtual, setVirtual] = useState(false);

	function onSubmit(values, e) {
		/* console.log("Body u FilterComponent: "); */

		let filter = {
			radius: radius,
			virtual: virtual,
			order: sort ? "ATOZ" : "ZTOA",
		};
		/* console.log(JSON.stringify(filter)); */

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
		<div className="filterZahtjeva">
			<Form unstackable onSubmit={handleSubmit(onSubmit)}>
				<Form.Group inline>
					<Label for="radius">Radijus:</Label>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<Input
						labelPosition = "left corner"
						type="text"
						name="address"
						id="radius"
						placeholder="npr. 5 km"
						onChange={handleRadius}
					/>
				</Form.Group>
				<Form.Group inline>
					<Label for="radius">Sortiranje: </Label>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<CustomInput
						type="radio"
						id="a_z"
						name="customRadio"
						label="A - Z"
						onChange={handleSortAZ}
					/>
					<span>&nbsp;&nbsp;&nbsp;</span>
					<CustomInput
						type="radio"
						id="z_a"
						name="customRadio"
						label="Z - A"
						onChange={handleSortZA}
					/>
				</Form.Group>
				<Form.Group inline>
					<Form.Input
						type="checkbox"
						name="check"
						id="exampleCheck"
						onClick={handleVirtual}
					/>
					<Label for="exampleCheck" check>
						Virtualni
					</Label>
				</Form.Group>
				<Form.Group>
					<Button inline type="submit" size="small">
						Filtriraj
					</Button>
				</Form.Group>
			</Form>
		</div>
	);
}

export default FilterComponent;
