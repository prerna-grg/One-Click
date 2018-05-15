function validateSignupForm() {
    var fname = document.forms["SignupForm"]["first_name"].value;
    var lname = document.forms["SignupForm"]["second_name"].value;
    var email = document.forms["SignupForm"]["email"].value;
    var pas1 = document.forms["SignupForm"]["pass1"].value;
    var pas2 = document.forms["SignupForm"]["pass2"].value;
    email=email.trim();
    if (fname === "" ||lname === ""||email === ""||pas1 === ""||pas2 === "") {
        alert("All enteries are not filled");
        return false;
    }
    if ( fname.length>20 )
    {
    	alert("firstname must be of 1 to 20 characters.");
    	return false;
    }
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if(email.match(mailformat))
	{var arr = email.split("@");
		if(arr[1]!="iitrpr.ac.in")
			{
			alert("Domain name should be \"iitrpr.ac.in\" ");
			return false;
			}
		return true;
	
	}
	else
	{
	alert("You have entered an invalid email address!");
	return false;
	}

    if ( fname.length>20 ||lname.length>20 )
    {
    	alert("Last name must be of 1 to 20 characters.");
    	return false;
    }
    if(pas1.length>20 || pas1.length <8||pas2.length>20|| pas2.length <8)
    {
    	alert("Password must be of 8 to 20 characters.");
    	return false;
    }
    if(pas1!=pas2)
    {
    	alert("Password does not match with Confirm Password");
    	return false;
    }
}

