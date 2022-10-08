const open_register = () =>{
	var signupPanel = document.getElementById("register-panel");
	var signinPanel = document.getElementById("login-panel");
	
	signupPanel.style.right = "0";
	signinPanel.style.right = "-100%"
}

const open_login = () =>{
    var signupPanel = document.getElementById("register-panel");
	var signinPanel = document.getElementById("login-panel");
	
	signupPanel.style.right = "-100%";
	signinPanel.style.right = "0"
}

function loginFormValid(){
	document.getElementById("login_status").style.display = "none";
	var username = document.getElementById("login-username")
	var password = document.getElementById("login-password")
	var warning = document.getElementById("error_valid")

	if(username.value === "" && password.value === ""){
		warning.innerHTML = '<i class="fa-solid fa-square-xmark"></i><br/>Username and Password<br/>must be not empty';
		document.getElementById("login-username").style.border = "1px solid red"
		document.getElementById("login-password").style.border = "1px solid red"
		return false;
	}
	if(username.value === "" || username.length < 3){
		warning.innerHTML = '<i class="fa-solid fa-square-xmark"></i><br/>Username must be than more 3 characters';
		document.getElementById("login-username").style.border = "1px solid red"
		return false;
	}
	if(password.value === "" || password.length < 5){
			warning.innerHTML = '<i class="fa-solid fa-square-xmark"></i><br/>Password must be than more 5 characters';
			document.getElementById("login-password").style.border = "1px solid red"
			return false;
	}
	
	return true;
}

function registerFormValid(){
	var fullname = document.getElementById("register_fullname");
	var email = document.getElementById("register_email");
	var username = document.getElementById("register_username");
	var password = document.getElementById("register_password");
	var error = document.getElementById("error_valid_register");
	
	if(fullname.value === "" 
	&& password.value === "" 
	&& email.value === "" 
	&& username.value === ""){
		fullname.style.border = "1px solid red"
		email.style.border = "1px solid red"
		username.style.border = "1px solid red"
		password.style.border = "1px solid red"
		error.innerHTML = '<i class="fa-solid fa-square-xmark"></i><br/>These fields cannot be empty';
		return false;
	}
	if(fullname.value === "" && fullname.value.length < 3){
		fullname.style.border = "1px solid red"
		error.innerHTML = '<i class="fa-solid fa-square-xmark"></i><br/>Full name must be than more 3 characters';
		return false;
	}
	if(password.value === "" && password.value.length < 6){
		password.style.border = "1px solid red"
		error.innerHTML = '<i class="fa-solid fa-square-xmark"></i><br/>Password must be than more 5 characters';
		return false;
	}
	if(username.value === "" && username.value.length < 3){
		username.style.border = "1px solid red"
		error.innerHTML = '<i class="fa-solid fa-square-xmark"></i><br/>Username must be than more 3 characters';
		return false;
	}
	if(email.value === "" && email.value.length < 3){
		email.style.border = "1px solid red"
		error.innerHTML = '<i class="fa-solid fa-square-xmark"></i><br/>The format of the email is incorrect';
		return false;
	}
	
	return true;
	
}
