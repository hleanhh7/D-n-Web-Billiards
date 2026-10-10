$ErrorActionPreference = 'Stop'
$previousJavaHome = $env:JAVA_HOME
$previousDbPassword = $env:DB_PASSWORD
$billiardsExit = 1

Push-Location (Join-Path $PSScriptRoot 'trananh-billiards')
try {
    if ([string]::IsNullOrWhiteSpace($env:JAVA_HOME)) {
        $env:JAVA_HOME = 'C:\Program Files\Java\jdk-25'
    }
    if (-not (Test-Path (Join-Path $env:JAVA_HOME 'bin\java.exe'))) {
        throw 'JDK not found. Set JAVA_HOME to your JDK 25 installation.'
    }

    # Existing connection settings are inherited; application.properties supplies defaults.
    if ([string]::IsNullOrEmpty($env:DB_PASSWORD)) {
        $billiardsUser = if ($env:DB_USERNAME) { $env:DB_USERNAME } else { 'postgres' }
        $securePassword = Read-Host "PostgreSQL password for $billiardsUser" -AsSecureString
        if ($securePassword.Length -eq 0) {
            throw 'Password is empty. Run again and enter your PostgreSQL password.'
        }
        $credential = New-Object System.Management.Automation.PSCredential($billiardsUser, $securePassword)
        $env:DB_PASSWORD = $credential.GetNetworkCredential().Password
    }

    $billiardsPort = if ($env:SERVER_PORT) { $env:SERVER_PORT } else { '8081' }
    Write-Host "Starting Billiards. After the Started message, open http://localhost:$billiardsPort/"
    Write-Host 'Press Ctrl+C to stop.'
    & .\mvnw.cmd spring-boot:run
    $billiardsExit = $LASTEXITCODE
} catch {
    Write-Host $_.Exception.Message -ForegroundColor Red
} finally {
    $env:DB_PASSWORD = $previousDbPassword
    $env:JAVA_HOME = $previousJavaHome
    if ($null -ne $securePassword) { $securePassword.Dispose() }
    Remove-Variable credential, securePassword, previousDbPassword -ErrorAction SilentlyContinue
    Pop-Location
}

exit $billiardsExit
