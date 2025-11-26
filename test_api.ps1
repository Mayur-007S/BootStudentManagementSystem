$baseUrl = "http://localhost:8085/api"
$results = @()

function Log-Result {
    param ($TestName, $Method, $Url, $Status, $Output)
    $obj = [PSCustomObject]@{
        TestName = $TestName
        Method   = $Method
        Url      = $Url
        Status   = $Status
        Output   = $Output
    }
    $global:results += $obj
    Write-Host "[$Status] $TestName"
}

function Get-ErrorDetails {
    param ($ExceptionObject)
    $msg = $ExceptionObject.Exception.Message
    if ($ExceptionObject.Exception.Response) {
        try {
            $stream = $ExceptionObject.Exception.Response.GetResponseStream()
            if ($stream) {
                $reader = New-Object System.IO.StreamReader($stream)
                $responseBody = $reader.ReadToEnd()
                $msg += " | Body: $responseBody"
            }
        }
        catch {
            $msg += " | Could not read response body"
        }
    }
    return $msg
}

$deptId = $null
$sid = $null

# 1. Add Department
try {
    $body = @{ deptname = "HR"; location = "Building B" } | ConvertTo-Json
    $response = Invoke-RestMethod -Method Post -Uri "$baseUrl/dept" -ContentType "application/json" -Body $body -ErrorAction Stop
    Log-Result "Add Department" "POST" "$baseUrl/dept" "Success" $response
    if ($response -match "Id : (\d+)") { $deptId = $matches[1] }
}
catch {
    Log-Result "Add Department" "POST" "$baseUrl/dept" "Failed" (Get-ErrorDetails $_)
}

# 2. Get All Departments
try {
    $departments = Invoke-RestMethod -Method Get -Uri "$baseUrl/dept" -ErrorAction Stop
    Log-Result "Get All Departments" "GET" "$baseUrl/dept" "Success" ($departments | ConvertTo-Json -Depth 2)
    if (-not $deptId -and $departments -is [System.Array] -and $departments.Count -gt 0) {
        $deptId = $departments[$departments.Count - 1].deptid
    }
}
catch {
    Log-Result "Get All Departments" "GET" "$baseUrl/dept" "Failed" (Get-ErrorDetails $_)
}

if ($deptId) {
    # 3. Get One Department
    try {
        $oneDept = Invoke-RestMethod -Method Get -Uri "$baseUrl/onedept?deptid=$deptId" -ErrorAction Stop
        Log-Result "Get One Department" "GET" "$baseUrl/onedept?deptid=$deptId" "Success" ($oneDept | ConvertTo-Json -Depth 2)
    }
    catch {
        Log-Result "Get One Department" "GET" "$baseUrl/onedept?deptid=$deptId" "Failed" (Get-ErrorDetails $_)
    }

    # 4. Add Student
    try {
        $studentBody = @{
            name  = "Alice"
            marks = "90"
            role  = "FullStack"
            dept  = @{ deptid = $deptId }
        } | ConvertTo-Json
        $addStudentResponse = Invoke-RestMethod -Method Post -Uri "$baseUrl/student" -ContentType "application/json" -Body $studentBody -ErrorAction Stop
        Log-Result "Add Student" "POST" "$baseUrl/student" "Success" $addStudentResponse
        if ($addStudentResponse -match "Id : (\d+)") { $sid = $matches[1] }
    }
    catch {
        Log-Result "Add Student" "POST" "$baseUrl/student" "Failed" (Get-ErrorDetails $_)
    }
}

# 5. Get All Students
try {
    $students = Invoke-RestMethod -Method Get -Uri "$baseUrl/student" -ErrorAction Stop
    Log-Result "Get All Students" "GET" "$baseUrl/student" "Success" ($students | ConvertTo-Json -Depth 2)
    if (-not $sid -and $students -is [System.Array] -and $students.Count -gt 0) {
        $sid = $students[$students.Count - 1].sid
    }
}
catch {
    Log-Result "Get All Students" "GET" "$baseUrl/student" "Failed" (Get-ErrorDetails $_)
}

if ($sid) {
    # 6. Get One Student
    try {
        $oneStudent = Invoke-RestMethod -Method Get -Uri "$baseUrl/onestudent?sid=$sid" -ErrorAction Stop
        Log-Result "Get One Student" "GET" "$baseUrl/onestudent?sid=$sid" "Success" ($oneStudent | ConvertTo-Json -Depth 2)
    }
    catch {
        Log-Result "Get One Student" "GET" "$baseUrl/onestudent?sid=$sid" "Failed" (Get-ErrorDetails $_)
    }

    # 7. Filter Students
    try {
        $filtered = Invoke-RestMethod -Method Get -Uri "$baseUrl/studentFilter" -ErrorAction Stop
        Log-Result "Filter Students (FullStack)" "GET" "$baseUrl/studentFilter" "Success" ($filtered | ConvertTo-Json -Depth 2)
    }
    catch {
        Log-Result "Filter Students (FullStack)" "GET" "$baseUrl/studentFilter" "Failed" (Get-ErrorDetails $_)
    }

    # 8. Update Student
    try {
        $updateBody = @{
            name  = "AliceUpdated"
            marks = "95"
            role  = "FullStack"
            dept  = @{ deptid = $deptId }
        } | ConvertTo-Json
        $updatedStudent = Invoke-RestMethod -Method Put -Uri "$baseUrl/student/$sid" -ContentType "application/json" -Body $updateBody -ErrorAction Stop
        Log-Result "Update Student" "PUT" "$baseUrl/student/$sid" "Success" ($updatedStudent | ConvertTo-Json -Depth 2)
    }
    catch {
        Log-Result "Update Student" "PUT" "$baseUrl/student/$sid" "Failed" (Get-ErrorDetails $_)
    }

    # 9. Get Student By Name
    try {
        $byName = Invoke-RestMethod -Method Get -Uri "$baseUrl/studentName?name=AliceUpdated" -ErrorAction Stop
        Log-Result "Get Student By Name" "GET" "$baseUrl/studentName?name=AliceUpdated" "Success" ($byName | ConvertTo-Json -Depth 2)
    }
    catch {
        Log-Result "Get Student By Name" "GET" "$baseUrl/studentName?name=AliceUpdated" "Failed" (Get-ErrorDetails $_)
    }

    # 10. Get Student By Name and Marks
    try {
        $byNameMarks = Invoke-RestMethod -Method Get -Uri "$baseUrl/studDept?name=AliceUpdated&marks=95" -ErrorAction Stop
        Log-Result "Get Student By Name & Marks" "GET" "$baseUrl/studDept?name=AliceUpdated&marks=95" "Success" ($byNameMarks | ConvertTo-Json -Depth 2)
    }
    catch {
        Log-Result "Get Student By Name & Marks" "GET" "$baseUrl/studDept?name=AliceUpdated&marks=95" "Failed" (Get-ErrorDetails $_)
    }

    # 11. Delete Student
    try {
        $deleteResponse = Invoke-RestMethod -Method Post -Uri "$baseUrl/student/$sid" -ErrorAction Stop
        Log-Result "Delete Student" "POST" "$baseUrl/student/$sid" "Success" $deleteResponse
    }
    catch {
        Log-Result "Delete Student" "POST" "$baseUrl/student/$sid" "Failed" (Get-ErrorDetails $_)
    }
}

$global:results | ConvertTo-Json -Depth 4 | Out-File "api_test_results.json"
