import React, { useEffect, useState } from "react";

function CommentComponent(props) {
    const [ratings, setRatings] = useState("");

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
		if (props.listaKomentara) {
            console.log("bilo");
			setRatings(props.listaKomentara);
		} else if (props.username) {
            console.log("fečam");
			fetch("http://localhost:8080/rating/of/" + props.username, options)
				.then((response) => response.text())
				.then((result) => {
						setRatings(
							JSON.parse(result)
						);
					    }
			        )
				.catch((error) => {
					console.log("error: ", error, "LISTA VJEROJATNO PRAZNA");
					setRatings("");
				});
        }
        else{
            console.log("OVO SE NIKAD NE SMIJE ISPISATI!");
        }
	}, [props.listaKomentara]);
    
    if(ratings){
        return (
            <div className="container">
                <div className="col-md-12">
                    <div className="bg-white rounded shadow-sm p-4 mb-4 restaurant-detailed-ratings-and-reviews">
                    {ratings.map((r) => (<div>
                        <div className="reviews-members pt-4 pb-4">
                            <div className="media">
                                <img alt="Generic placeholder image" src="http://bootdey.com/img/Content/avatar/avatar1.png" className="mr-3 rounded-pill" />
                                <div className="media-body">
                                    <div className="reviews-members-header">
                                        <span className="star-rating float-right">
                                        {r.rating >= 0.5 ? <span className="fa fa-star checked"></span> : <span className="fa fa-star"></span>}
                                        {r.rating >= 1.5 ? <span className="fa fa-star checked"></span> : <span className="fa fa-star"></span>}
                                        {r.rating >= 2.5 ? <span className="fa fa-star checked"></span> : <span className="fa fa-star"></span>}
                                        {r.rating >= 3.5 ? <span className="fa fa-star checked"></span> : <span className="fa fa-star"></span>}
                                        {r.rating >= 4.5 ? <span className="fa fa-star checked"></span> : <span className="fa fa-star"></span>}
                                        </span>
                                        <h6 className="mb-1"><div role="button">{r.reviewer.username}</div></h6>
                                        <p className="text-gray">{r.request === null ? (<i>Ocjena korisnika</i>) : (<i>Ocjena zahtjeva</i>)}</p>
                                    </div>
                                    <div className="reviews-members-body">
                                        <p>{r.comment}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr />
                    </div>))}
    
                    </div>
    
    
                </div>
            </div>
            
        );
    }
    return( null )
        
    
    
}
export default CommentComponent;