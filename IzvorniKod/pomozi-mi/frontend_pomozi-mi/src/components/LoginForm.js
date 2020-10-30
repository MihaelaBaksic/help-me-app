import React from "react";
import logo from "./resources/zec.png";

function LoginForm() {
	return (
		<div>
			<div className="formHeader">
				<img className="formLogo" src={logo} alt="neradi mi slika" />
				<div className="kratkiOpis">Spremno iščekujemo Vašu pomoć</div>
			</div>
			<form>
				<div className="form-group">
					<input
						name="username"
						className="form-control"
						placeholder="Unesite username"
					></input>
				</div>
				<div className="form-group">
					<input
						name="password"
						type="password"
						className="form-control"
						placeholder="Unesite lozinku"
					></input>
				</div>

				<div class="form-group form-check">
					<input type="checkbox" class="form-check-input" />
					<label class="form-check-label">Zapamti me?</label>
				</div>
				<div className="loginRegisterGumbi">
					<button type="submit" class="btn btn-primary btn-lg">
						Login
					</button>
					<button type="submit" class="btn btn-secondary btn-lg">
						Register
					</button>
				</div>
			</form>
		</div>
	);
}

export default LoginForm;
