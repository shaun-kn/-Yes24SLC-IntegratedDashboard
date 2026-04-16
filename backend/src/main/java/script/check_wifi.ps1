# ===== 설정값 =====
$TargetIP = "172.16.50.166"              # ping 체크 대상
$SSID = "KENGIC"          # 자동 재연결할 Wi-Fi 이름
$AdapterName = "Wi-Fi"             # 무선 어댑터 이름
$CheckIntervalSec = 5             # 점검 주기
$FailThreshold = 3                 # 연속 실패 횟수
$ReconnectWaitSec = 8              # 재연결 후 대기 시간
$LogFile = "C:\temp\wifi_reconnect.log"

$FailCount = 0

# ===== 로그 함수 =====
function Write-Log($msg) {
    $time = Get-Date -Format "yyyy-MM-dd HH:mm:ss"
    $line = "$time $msg"
    Write-Host $line
    Add-Content -Path $LogFile -Value $line
}

# ===== Wi-Fi 연결 상태 확인 =====
function Get-WifiState {
    $output = netsh wlan show interfaces
    if ($output -match "^\s*State\s*:\s*(.+)$") {
        return $Matches[1].Trim()
    }
    return "Unknown"
}

# ===== 현재 연결된 SSID 확인 =====
function Get-ConnectedSSID {
    $output = netsh wlan show interfaces
    $lines = $output -split "`r?`n"

    foreach ($line in $lines) {
        if ($line -match "^\s*SSID\s*:\s*(.+)$" -and $line -notmatch "BSSID") {
            return $Matches[1].Trim()
        }
    }
    return ""
}

# ===== SSID 재연결 =====
function Connect-Wifi {
    Write-Log "Wi-Fi 재연결 시도: SSID=$SSID"
    netsh wlan connect name="$SSID" | Out-Null
    Start-Sleep -Seconds $ReconnectWaitSec
}

# ===== 어댑터 재시작 =====
function Restart-WifiAdapter {
    Write-Log "Wi-Fi 어댑터 재시작: $AdapterName"
    Disable-NetAdapter -Name $AdapterName -Confirm:$false -ErrorAction SilentlyContinue
    Start-Sleep -Seconds 3
    Enable-NetAdapter -Name $AdapterName -Confirm:$false -ErrorAction SilentlyContinue
    Start-Sleep -Seconds 5
}

# ===== 시작 =====
if (!(Test-Path (Split-Path $LogFile))) {
    New-Item -ItemType Directory -Path (Split-Path $LogFile) -Force | Out-Null
}

Write-Log "Wi-Fi 감시 시작"

while ($true) {
    $pingOk = Test-Connection -ComputerName $TargetIP -Count 1 -Quiet -ErrorAction SilentlyContinue

    if ($pingOk) {
        $currentSsid = Get-ConnectedSSID
        Write-Log "Ping OK / Connected SSID: $currentSsid"
        $FailCount = 0
    }
    else {
        $FailCount++
        Write-Log "Ping Fail ($FailCount/$FailThreshold)"
    }

    if ($FailCount -ge $FailThreshold) {
        $wifiState = Get-WifiState
        $currentSsid = Get-ConnectedSSID
        Write-Log "연속 실패 감지 / State=$wifiState / SSID=$currentSsid"

        # 1차: 저장된 SSID로 재연결
        Connect-Wifi

        # 재확인
        $pingOkAfterReconnect = Test-Connection -ComputerName $TargetIP -Count 1 -Quiet -ErrorAction SilentlyContinue
        if (-not $pingOkAfterReconnect) {
            Write-Log "SSID 재연결 실패, 어댑터 재시작 진행"

            # 2차: 어댑터 재시작
            Restart-WifiAdapter

            # 3차: 다시 연결
            Connect-Wifi
        }

        $finalPing = Test-Connection -ComputerName $TargetIP -Count 1 -Quiet -ErrorAction SilentlyContinue
        if ($finalPing) {
            Write-Log "복구 성공"
        }
        else {
            Write-Log "복구 실패"
        }

        $FailCount = 0
    }

    Start-Sleep -Seconds $CheckIntervalSec
}