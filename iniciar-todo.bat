
@echo off
REM ✅ ARREGLADO: Path correcto
cd /d "%~dp0"  ← Va a la carpeta raíz del proyecto

start "Eureka" cmd /k "cd eureka && .\mvnw spring-boot:run"
timeout /t 15  ← Espera 15 segundos a que Eureka esté listo

start "MS_Cliente" cmd /k "cd ms_cliente && .\mvnw spring-boot:run"
start "MS_Comic" cmd /k "cd ms_comic && .\mvnw spring-boot:run"

REM ✅ ARREGLADO: Path completo correcto
start "MS_Tiendas" cmd /k "cd tiendas(1)\tiendas && .\mvnw spring-boot:run"

start "API_Gateway" cmd /k "cd apiGateway && .\mvnw spring-boot:run"

echo ✅ Swagger en: http://localhost:8080/swagger-ui.html