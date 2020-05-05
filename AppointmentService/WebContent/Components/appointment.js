$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
	{
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
}
);

//SAVE======================================
$(document).on("click", "#btnSave", function(event)
{
	//clear alerts-----------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	//Form validation-------------------------
	var status = validationAppointmentForm();
	if(status != true)
	{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	
	//If valid-------------------------
	var type = ($("#hidAppIDSave").val() == "" ) ? "POST"  : "PUT";
	
	
	$.ajax(
	{
		url : "AppointmentAPI",
		type : type,
		data : $("#formAppointment").serialize(),
		dataType : "text",
		complete : function(response, status)
		{   
			onAppointmentSaveComplete(response.responseText, status);
		}
	}); 
});

function onAppointmentSaveComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show(); 

			$("#divAppGrid").html(resultSet.data);
			} else if (resultSet.status.trim() == "error")
			{
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			} 

	} else if (status == "error")
	{
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	} 

	$("#hidAppIDSave").val("");
	$("#formAppointment")[0].reset();
	}

//UPDATE=========================================================
$(document).on("click", ".btnUpdate",function(event)
{
	//$("#hidAppIDSave").val(
			//$(this).closest("tr").find('#hidAppIDUpdate').val());
	console.log($(this).closest("tr"));
	document.getElementById("hidAppIDSave").value = "Update";
	$("#id").val($(this).closest("tr").find('td:eq(0)').text());
	$("#hospitalid").val($(this).closest("tr").find('td:eq(1)').text());
	$("#patientid").val($(this).closest("tr").find('td:eq(2)').text());
	$("#date").val($(this).closest("tr").find('td:eq(3)').text());
	$("#time").val($(this).closest("tr").find('td:eq(4)').text());
	$("#description").val($(this).closest("tr").find('td:eq(5)').text());
	//$("#status").val($(this).closest("tr").find('td:eq(6)').bool());
	
});

//DELETE==========================================================
$(document).on("click", ".btnRemove", function(event)
{
	$.ajax(
			{
				url : "AppointmentAPI",
				type : "DELETE",
				data : "id=" + $(this).data("id"),
				dataType : "text",
				complete : function(response, status)
				{
					onAppointmentDeleteComplete(response.responseText, status);
				}
			});
	}); 

function onAppointmentDeleteComplete(response, status)
{
	if (status == "success")
	{
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show(); 

			$("#divAppGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error")
		{ 
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		} 

	} else if (status == "error")
	{
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else
	{
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

//CLIENT-MODEL============================================
function validationAppointmentForm()
{
	//ID
	if($("#id").val().trim() == "")
	{
		return "Insert Appointment id.";
	}
	
	//hospitalid
	if($("#hospitalid").val().trim() == "")
	{
		return "Insert Hospital id.";
	}
	
	//patientid
	if($("#patientid").val().trim() == "")
	{
		return "Insert Patient id.";
	}
	
	//date
	if($("#date").val().trim() == "")
	{
		return "Insert date.";
	}
	
	//time
	if($("#time").val().trim() == "")
	{
		return "Insert time.";
	}
	
	//description
	if($("#description").val().trim() == "")
	{
		return "Insert description.";
	}
	
	//Status
	if($("#status").val().trim() == "")
	{
		return "Insert status.";
	}
	
	
	return true;
}