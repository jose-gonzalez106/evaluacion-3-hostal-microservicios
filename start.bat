@echo off

echo Iniciando Eureka...
cd eureka
start cmd /k "mvnw spring-boot:run"

echo Esperando 15 segundos a que Eureka se estabilice...
timeout /t 15 /nobreak > nul

echo Iniciando Huespedes...
cd ..\huespedes
start cmd /k "mvnw spring-boot:run"

echo Esperando 10 segundos...
timeout /t 10 /nobreak > nul

echo Iniciando Hostal Cordillera...
cd "..\hostal cordillera"
start cmd /k "mvnw spring-boot:run"

echo Esperando 10 segundos...
timeout /t 10 /nobreak > nul

echo Iniciando Gateway...
cd ..\gateway
start cmd /k "mvnw spring-boot:run"

echo Ecosistema lanzado.
echo Eureka dashboard: http://localhost:8761
echo Huespedes: http://localhost:8081
echo Gateway: http://localhost:8080
pause