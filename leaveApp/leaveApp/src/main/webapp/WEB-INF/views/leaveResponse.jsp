<!DOCTYPE html>
<html>
<head>
    <title>Leave Application Response</title>
</head>
<body>
     <h2>Leave Details:</h2>
    <p>Employee Id: ${leaveResponse.employee.id}</p>
    <p>Number of working days leaves being applied for: ${leaveResponse.workingDays}</p>
    <p>Leaves Taken So Far : ${leaveResponse.totalLeavesTaken}</p>
    <p>Number of balance leaves remaining once this leave is approved: ${leaveResponse.balanceLeaves}</p>
</body>
</html>
