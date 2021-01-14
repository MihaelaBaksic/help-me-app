import React from "react";
import { useForm } from "react-hook-form";

const baseUrl = `${process.env.PUBLIC_URL}`;
//const baseUrl = "http://localhost:8080";

function CommentFormComponent(props) {
	const { register, handleSubmit, errors, reset } = useForm();

	/* const onSubmit = (data) => console.log(data); */
	async function onSubmit(values, e) {
		e.preventDefault();
		/* console.log("aaaaaaa!");
		console.log(values); */
		/* values = JSON.stringify(values); */
		var myHeaders = new Headers();
		myHeaders.append(
			"Authorization",
			"Basic " + sessionStorage.getItem("basicAuthToken")
		);
		myHeaders.append("Content-Type", "application/json");
		if (props.requestId) {
			values.requestId = props.requestId;
		}
		if (props.korIme) {
			values.ratedUsername = props.korIme;
		}
		const options = {
			method: "POST",
			headers: myHeaders,
			body: JSON.stringify(values),
		};
		fetch(baseUrl + "/rating", options)
			.then((response) => response.text())
			.then((result) => {
				/* console.log(JSON.parse(result)); */
				reset(JSON.stringify(values));
				props.reRenderaj();
			})
			.catch((error) => console.log(error));
	}

	return (
		<div className="container">
			<div className="row" style={{ marginTop: "40px" }}>
				<div className="col-12">
					<div className="well well-sm">
						<div className="row" id="post-review-box">
							<div className="col-md-12">
								<form
									id="newRating"
									name="newRating"
									className="forma"
									onSubmit={handleSubmit(onSubmit)}
									onKeyPress={(e) => {
										e.key === "Enter" && e.preventDefault();
									}}
								>
									<div
										id="requestFormDescription"
										className="form-group"
									>
										<textarea
											name="comment"
											className="form-control"
											rows="5"
											cols="50"
											placeholder="Ovdje unesite komentar..."
											ref={register}
										/>
									</div>
									<div className="text-right mb-6">
										<div
											className="stars starrr"
											data-rating="0"
										>
											<div className="rate">
												<input
													type="radio"
													id="star5"
													name="rating"
													value="5"
													ref={register}
												/>
												<label
													htmlFor="star5"
													title="text"
												>
													5 stars
												</label>
												<input
													type="radio"
													id="star4"
													name="rating"
													value="4"
													ref={register}
												/>
												<label
													htmlFor="star4"
													title="text"
												>
													4 stars
												</label>
												<input
													type="radio"
													id="star3"
													name="rating"
													value="3"
													ref={register}
												/>
												<label
													htmlFor="star3"
													title="text"
												>
													3 stars
												</label>
												<input
													type="radio"
													id="star2"
													name="rating"
													value="2"
													ref={register}
												/>
												<label
													htmlFor="star2"
													title="text"
												>
													2 stars
												</label>
												<input
													type="radio"
													id="star1"
													name="rating"
													value="1"
													ref={register}
												/>
												<label
													htmlFor="star1"
													title="text"
												>
													1 star
												</label>
											</div>
										</div>
										<button
											type="submit"
											className="btn btn-primary"
											form="newRating"
										>
											Submit
										</button>
									</div>
									<input
										name="ratedUsername"
										style={{ display: "none" }}
										value={props.korIme}
										ref={register}
									/>
									<input
										name="requestId"
										style={{ display: "none" }}
										value="null"
										ref={register}
									/>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}
export default CommentFormComponent;
