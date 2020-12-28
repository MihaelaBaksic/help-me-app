function OtherUserProfile(props) {
    return (
        <div className="layout-content">
            <div className="container flex-grow-1 container-p-y">
                <div className="container-m-nx container-m-ny theme-bg-white mb-4">
                    <div className="media col-md-10 col-lg-8 col-xl-7 py-5 mx-auto">
                        <img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" className="d-block ui-w-100 rounded-circle" />

                        <div className="media-body ml-5">
                            <h4 className="font-weight-bold mb-4">Pero Perić</h4>

                            <a className="d-inline-block text-body ml-3">
                                <strong>51</strong>
                                <span className="text-muted">izvršenih zahtjeva</span>
                            </a>
                            <a className="d-inline-block text-body ml-3">
                                <strong>44</strong>
                                <span className="text-muted">postavljenih zahtjeva</span>
                            </a>
                            <div className="mt-3">
                                <a href="javascript:void(0)" className="btn btn-success rounded-pill">+&nbsp; Dodaj kao admina</a>
                            </div>
                            <div className="mt-3">
                                <a href="javascript:void(0)" className="btn btn-danger rounded-pill">+&nbsp; Blokiraj korisnika</a>
                            </div>

                        </div>

                    </div>

                    <hr className="m-0" />
                </div>

                <div className="row">
                    <div className="col">

                        <div className="card mb-4">
                            <div className="card-body">
                                <div className="col-xl-12">
                                    <div class="page-header">
                                        <h3>Postavljeni zahtjevi</h3> 
                                        <hr/>  
                                    </div>
                                    OVDJE JE KOMPONENTA POSTAVLJENIH ZAHTJEVA
                      </div>
                            </div>
                        </div>

                    </div>
                </div>

                <div className="row">
                    <div className="col">

                        <div className="card mb-4">
                            <div className="card-body">
                                <div className="col-xl-12">
                                    <div class="page-header">
                                        <h3>Izvršeni zahtjevi</h3> 
                                        <hr/>  
                                    </div>
                                    OVDJE JE KOMPONENTA IZVRŠENIH ZAHTJEVA
                      </div>
                            </div>
                        </div>

                    </div>
                    <div className="col-xl-4">

                        <div className="card mb-4">
                            
                            <div className="card-body">
                            <div class="page-header">
                                <h3>Komentari</h3> 
                                    <hr/>  
                            </div>
                                OVDJE JE KOMPONENTA KOMENTARA
                    </div>

                        </div>

                    </div>
                </div>
            </div>
        </div>
    );
}
export default OtherUserProfile;
