# 웹서버를 리눅스 기준으로 실행하기 위한 필요한 설치/빌드 방법

웹서버를 리눅스 기준으로 실행하는 방법은 
- plain jar 파일을 VM 에서 실행하는 방법
- 도커라이징 후 컨테이너 환경에서 실행하는 방법
- Kubernetes 환경에서 실행하는 방법 .. 등 다양하지만,

본문에서는 애플리케이션을 컨테이너 이미지로 패키징 후 컨테이너 환경 (docker)에서 실행하는 방법을 설명합니다.

## 1. 설치
빌드를 위해 필요한 설치는 jdk 와 skaffold 입니다.
웹서버 실행을 위해서는 docker 설치가 필요합니다.

* Ubuntu 20.04 (LTS) 기준 으로 작성됐습니다.

### 1-1.[SDKMAN](https://sdkman.io/) 설치

본문에서는 범용 패키지 관리 도구인 *SDKMAN* 을 이용하여 java 를 설치합니다.
따라서 java 설치전 sdkman 을 먼저 설치합니다.

[1] zip, unzip, curl 응용 프로그램 설치
```shell
sudo apt install zip unzip curl -y
```

[2] SDKMAN 설치
```shell
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
```

[3]  sdkman version 확인 (설치가 성공했는지 확인)
```shell
sdk version
```

### 1-2.JAVA 설치
[1] category-service 에서 사용중인 jdk 11 버전을 sdkman 을 이용하여 install 합니다.
```shell
sdk install java 11.0.17-tem
```
[2] java 가 설치됐는지 확인
```shell
java -version
```

### 1-3.[Skaffold](https://skaffold.dev/) 설치 
Dockerfile 대신 Skaffold 를 선택한 이유는 Skaffold의 빌드 속도가 Dockerfile 를 이용한 도커라이징보다 훨씬 빠르기 때문입니다.
이는 Skaffold 가 이미지 빌드를 할 때 Docker Daemon 이 필요하지 않고, Skaffold 자체적인 캐시를 사용하여 변경 사항만 이미지에 반영하기 때문입니다.

```shell
# For Linux x86_64 (amd64)
curl -Lo skaffold https://storage.googleapis.com/skaffold/releases/v2.0.0/skaffold-linux-amd64 && \
sudo install skaffold /usr/local/bin/
```

### 1-4. [Docker](https://www.docker.com/) 설치
```shell
# 패키지 업데이트
sudo apt-get update -y

sudo apt-get install ca-certificates software-properties-common apt-transport-https  gnupg lsb-release -y

# 도커 GPG key 추가
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg


# 도커 레파지토리 등록
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  
# 패키지 업데이트
sudo apt-get update

# docker 엔진 install
sudo apt-get install docker-ce docker-ce-cli containerd.io -y

# docker version 확인
sudo docker version

## 일반 유저에게도 docker 명령어 권한 부여시 아래 task 수행
# (선택) 일반 유저에게 docker 명령어 권한 부여
sudo usermod -aG docker $USER

# (선택) 해당 user 로 ssh 재 접속후 docker version 확인
docker version
```

웹서버 소스 git repository 를 clone 받습니다.
```shell
git clone https://github.com/jei0486/category-service
```

이제 필요한 설치 및 세팅은 끝났습니다.

## 2.빌드 및 컨테이너 이미지 생성

* skaffold 내용은 프로젝트 ROOT 위치의 skaffold.yaml 파일에 기재돼있습니다.

[1] category-service dir 로 이동
```shell
cd category-service
```
[2] skaffold 를 이용하여  프로젝트 build 및 docker image 생성

```shell
# 아래 명령어로 실행할경우 latest 라는 태그가 붙게됩니다.
skaffold build -p dev

# v1 이라는 태그를 붙이고 싶을경우, 아래와 같이 실행하면 됩니다.
VER=v1 skaffold build -p dev
```
[3] 생성된 docker image 확인
```shell
docker image ls
```

## 3. 웹서버 실행

생성된 이미지를 이용하여 웹서버를 실행시키겠습니다.
```shell
# 컨테이너 실행
docker run -d -p 8080:8080 --name category-service jei0486/category-service

# 8080 port 가 아닌 다른 port 로 실행시키고 싶을경우, 
# 여러 방법이 있지만 host port number 부분을 수정하여 실행하면 됩니다.
docker run -d -p <host port number>:8080 --name category-service jei0486/category-service

# 컨테이너 확인
docker ps
```
프로젝트 ROOT 폴더의 request.http 파일을 이용하여 VSCode, IntelliJ IDE 에서 http 요청을 테스트 해볼 수 있습니다.
