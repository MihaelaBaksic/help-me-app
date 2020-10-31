import React from 'react'
import logo from "./resources/zec.png";

function RegisterForm() {

   return (
		<div>
			<div className="formHeader">
				<img className="formLogo" src={logo} alt="neradi mi slika" />
				<div className="kratkiOpis">Spremno iščekujemo Vašu pomoć</div>
			</div>
         <div className="kratkiOpis">
            Unesite Vaše podatke
         </div>
         <div>
            <br/>
         </div>
			<form>
         <div className="form-group">
					<input
						name="ime"
						className="form-control"
						placeholder="Ime"
					></input>
				</div>
            <div className="form-group">
					<input
						name="prezime"
						className="form-control"
						placeholder="Prezime"
					></input>
				</div>
				<div className="form-group">
					<input
						name="username"
						className="form-control"
						placeholder="Username"
					></input>
				</div>
            <div className="form-group">
					<input
						name="email"
						className="form-control"
						placeholder="E-mail"
					></input>
				</div>
				<div className="form-group">
					<input
						name="password"
						type="password"
						className="form-control"
						placeholder="Lozinka"
					></input>
				</div>
            <div className="form-group">
					<input
						name="repeatPassword"
						type="password"
						className="form-control"
						placeholder="Potvrdite lozinku"
					></input>
				</div>
            <div className="form-group">
					<input
						name="telefon"
						className="form-control"
						placeholder="Telefon"
					></input>
				</div>

				<div className="loginRegisterGumbi">
					<button type="submit" className="btn btn-secondary btn-lg" onClick = {() => console.log('Napravit redirect')}>
						Register
					</button>
				</div>
			</form>
		</div>
	);
}

export default RegisterForm
