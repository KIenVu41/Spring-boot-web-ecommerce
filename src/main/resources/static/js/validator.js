function inputValidation(inputTxt){
	
    let regx = /^[0-9a-zA-Z ]+$/;
    let textField = document.getElementById("textField");
        
    if(inputTxt.value != '' ){
    
        if(inputTxt.value.length >= 8){
        
            if(inputTxt.value.match(regx)){
                textField.textContent = 'Good input';
                textField.style.color = "green";
                    
            }else{
                textField.textContent = 'only numbers, letters And White space';
                textField.style.color = "red";
            }  
        }else{
            textField.textContent = 'your input is less than 8 chracters';
            textField.style.color = "red";
        }	
    }else{
        textField.textContent = 'your input is empty';
        textField.style.color = "red";
    }
}

function checkEmail(str)
{
    let emailField = document.getElementById("emailField");
    let re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if(!re.test(str)) {
        emailField.textContent = "Please enter a valid email address";
        emailField.style.color = "red";
    }else {
        emailField.textContent = "good";
        emailField.style.color = "green";
    }
}

function passValidation(pass) {
    let passField = document.getElementById("passwordField");
    if(pass != '' ){
        if(pass.length >= 8){    
           if(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$/.test(pass)) {
                passField.textContent = 'good';
                passField.style.color = "green";
           }else {
                passField.textContent = "Passwords must be at least 8 characters long, but no more than 32, include at least 1 lowercase letter, 1 capital letter, 1 number";
                passField.style.color = "red";
           }
        }else{
            passField.textContent = 'your pass is less than 8 chracters';
            passField.style.color = "red";
        }	
    }else{
        passField.textContent = 'your input is empty';
        passField.style.color = "red";
    }
}

function passValidation2(pass) {
    let passField = document.getElementById("passwordField2");
    if(pass != '' ){
        if(pass.length >= 8){    
           if(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,32}$/.test(pass)) {
                passField.textContent = 'good';
                passField.style.color = "green";
           }else {
                passField.textContent = "Passwords must be at least 8 characters long, but no more than 32, include at least 1 lowercase letter, 1 capital letter, 1 number";
                passField.style.color = "red";
           }
        }else{
            passField.textContent = 'your pass is less than 8 chracters';
            passField.style.color = "red";
        }	
    }else{
        passField.textContent = 'your input is empty';
        passField.style.color = "red";
    }
}

function checkRepass(repass) {
    let pass =  document.getElementById("inputPassword2");
    let passField = document.getElementById("passwordField3");
    if(repass != pass.value) {
        passField.textContent = "Password does not match";
        passField.style.color = "red";
    }else {
        passField.textContent = "Your password is matched";
        passField.style.color = "green";
    }
}