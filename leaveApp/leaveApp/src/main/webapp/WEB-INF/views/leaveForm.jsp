<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Leave Application Form</title>
    <!-- Include Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>

<body>
<div class="container mt-5">
    <h2>Leave Application Form</h2>
    <form action="/leave" method="post" onsubmit="return validateDates()">
        <div class="form-group">
            <label for="empId">Employee ID:</label>
            <input type="text" class="form-control" id="empId" name="employeeId">
        </div>
        <div class="form-group">
            <label for="fromDate">From Date:</label>
            <input type="date" class="form-control" id="fromDate" name="fromDate">
        </div>
        <div class="form-group">
            <label for="toDate">To Date:</label>
            <input type="date" class="form-control" id="toDate" name="toDate">
        </div>
        <!-- Other form fields -->
        <button type="submit" class="btn btn-primary">Apply</button>
    </form>
</div>
<!-- Include Bootstrap JS -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
     function validateDates() {
        var fromDate = document.getElementById("fromDate").value;
        var toDate = document.getElementById("toDate").value;
        var currentDate = new Date();
        var fromDatestr = new Date(fromDate);
        if (fromDate > toDate) {
            alert("From Date must be before To Date.");
            return false;
        }
        if (fromDatestr < currentDate) {
            alert("From Date must be After or same to Date.");
            return false;
        }
        
        return true;
    } 
</script>
</body>

</html>
