var open = false;
function openSearchBar(){
	const icon = document.getElementById("side-bar-btn");
	open = !open;
	if(open){
		document.getElementById("side-bar").style.left = "0%";
		icon.innerHTML = '<i class="fa-solid fa-x"></i>';
	}else{
		document.getElementById("side-bar").style.left = "-100%"
		icon.innerHTML = '<i class="fa-solid fa-magnifying-glass"></i>';
	}
    
}

function closeUserBar(){
    document.getElementById("user_info_wrapper").style.right = "-100%";
}

function openUserBar(){
    document.getElementById("user_info_wrapper").style.right = "0%";
}

var fileSelected = null;
function uploadImage(file){
    if(file.files && file.files[0]){
        if(file.files[0].size > 500000){
            document.getElementById("label_upload").innerHTML = "<p class='text-danger'><i class='fa-solid fa-circle-xmark'></i> File too big</p>";
            file.files[0].remove;
        }else{
			document.getElementById("preview-image-selected").style.backgroundImage = "url("+URL.createObjectURL(file.files[0])+")";
           document.getElementById("preview-image-upload").style.display = "block";
        //   uploadImageToAPI(file.files[0]) 
           fileSelected = file.files[0];
           file.files[0].remove;
        }
    	}
    }
    
function confirmUpload(){
   uploadImageToAPI(fileSelected)  
}    
  
    
  function uploadImageToAPI(file){
	 var formdata = new FormData();
			formdata.append("image", file);
            var requestOptions = {
            method: 'POST',
            body: formdata,
            redirect: 'follow'
            };
			const username = document.getElementById("username_field").value;
            fetch("/user/upload_image/"+username, requestOptions)
            .then(response => response.json())
            .then(result => {
                document.getElementById("label_upload").innerHTML = "<p class='text-success'><i class='fa-solid fa-circle-check'></i> Profile image changed</p>";
            	document.getElementById("image-selected").style.backgroundImage = "url(/user/images/"+username+")";
            	document.getElementById("image-selected").style.backgroundImage = "url("+URL.createObjectURL(file)+")";
            	document.getElementById("preview-image-upload").style.display = "none";
       
            })
              .catch(error => console.log('error', error));

}

function cancelUpload(){
	document.getElementById("preview-image-upload").style.display = "none";
	fileSelected = null;
	
}


