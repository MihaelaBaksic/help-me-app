function CommentComponent(props) {
    return (
        <div className="container">
            <div className="col-md-12">
                <div className="bg-white rounded shadow-sm p-4 mb-4 restaurant-detailed-ratings-and-reviews">
                    <div className="reviews-members pt-4 pb-4">
                        <div className="media">
                            <img alt="Generic placeholder image" src="http://bootdey.com/img/Content/avatar/avatar1.png" className="mr-3 rounded-pill" />
                            <div className="media-body">
                                <div className="reviews-members-header">
                                    <span className="star-rating float-right">
                                    <span className="fa fa-star checked"></span>
							<span className="fa fa-star checked"></span>
							<span className="fa fa-star checked"></span>
							<span className="fa fa-star checked"></span>
							<span className="fa fa-star"></span>
                                    </span>
                                    <h6 className="mb-1"><div role="button">Obrad Obradović</div></h6>
                                    <p className="text-gray">Odlazak po kruh</p>
                                </div>
                                <div className="reviews-members-body">
                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed at turpis ante. Etiam ultricies erat at urna viverra, sit amet ornare arcu venenatis. Nullam finibus blandit aliquam. In hac habitasse platea dictumst. Ut eget leo sit amet velit facilisis sollicitudin quis imperdiet ligula. Maecenas vel ipsum auctor, mattis lectus et, posuere ligula. Praesent ac ullamcorper est. </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr />
                    <div className="reviews-members pt-4 pb-4">
                        <div className="media">
                            <img alt="Generic placeholder image" src="http://bootdey.com/img/Content/avatar/avatar6.png" className="mr-3 rounded-pill" />
                            <div className="media-body">
                                <div className="reviews-members-header">
                                    <span className="star-rating float-right">
                                    <span className="fa fa-star checked"></span>
							<span className="fa fa-star checked"></span>
							<span className="fa fa-star checked"></span>
							<span className="fa fa-star"></span>
							<span className="fa fa-star"></span>
                                    </span>
                                    <h6 className="mb-1"><div role="button">Snjeguljica Macić</div></h6>
                                    <p className="text-gray"><i>Ocjena korisnika</i></p>
                                </div>
                                <div className="reviews-members-body">
                                    <p>Nulla mollis mollis orci, non volutpat nulla tempus non. Nullam sit amet purus libero. Vivamus id nisl purus. Praesent sodales quam et sapien tristique mattis. Nunc porttitor a ex ut pulvinar. Nullam felis sem, rutrum fringilla mi vitae, eleifend feugiat nibh. Integer et feugiat magna. Nullam suscipit laoreet condimentum. Proin ornare laoreet ante quis dignissim. Nullam est risus, vestibulum at augue sed, semper feugiat diam. Duis eleifend nunc eget pellentesque pharetra. Nulla faucibus posuere condimentum. Pellentesque ultrices magna id mauris blandit aliquam. Aliquam erat volutpat.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr />

                </div>


            </div>
        </div>
    );
}
export default CommentComponent;