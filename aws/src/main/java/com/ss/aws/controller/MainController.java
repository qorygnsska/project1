package com.ss.aws.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MainController {

	// 고정ip : 43.200.23.253
	
	@GetMapping("/aws/v1")
	public String main(
			@RequestParam(defaultValue = "1") Integer number) {
		if(number == 1) {
			log.info("/aws/v1 호출이 됩니다! info로그#############");
		}else if(number == -1) {
			log.error("/aws/v1 호출이 됩니다! error로그#############");
		}else if(number == 0) {
			log.warn("/aws/v1 호출이 됩니다! warn로그#############");
		}
		return "<h1> aws v1</h1>"; 		
	}	
}

/* 배포 
 * 1. EC2 고정 ip 할당 받기
 * 2. 모바텀으로 ssh 연결하기 
 * 3. git EC2 (내 컴퓨터에 다운로드 되어있는지 확인)
 *    git --version
 *    git 버전이 나올 시에는 설치 안해도됨!
 *    만약 버전이 안나오면!
 *    git 을 apt를 이용해서 git을 다운 받으면 됨
 *    
 *    apt 사용시 apt목록을 업데이트!
 *    
 *    실제 파일을 다운로드 받으면 폴더 안으로 들어가서
 *    그래들과 관련된 파일을 찾는다.
 *    그래들을 실행할수 있도록 권한을 설정!
 *    chmod u+x 파일명
 * 
 *    폴더 삭제 
 *    rm -rf aws(파일명)
 *    -r 폴더와 폴더 하위 폴더 모두 삭제
 *    -f 강제 삭제(파일 삭제시 확인안함)
 *    
 *    다시 클론해서 실행파일로 변경! gradlew
 *    
 *    그래들을 이용해서 빌드를 하게 되면 java파일을
 *    컴파일할 JDK가 없다고 뜬다.
 *    
 *    apt를 이용해서 자바JDK설치
 * 		sudo apt-cache search jdk
 *      sudo apt-cache search jdk | grep openjdk-11
 *      sudo apt install openjdk-11-jdk
 *      다운 완료시 확인
 *      java --version 
 *      
 *    정상적으로 실행이 된다. 단! SSH연결을 해서 
 *    파일을 실행했는데는 동작이되지만 우리가 ctrl+c
 *    종료를 누르면 실행파일 꺼지면서 사이트에 접속 안됨
 *    
 *    백그라운드에서 계속 동작할 수있도록 설정
 *    nohup 명령어
 *     리눅스에서 프로세스를 실행한 터미널의 세션이
 *     끊어지더라도 지속적으로 동작할 수있도록 실행해주는
 *     명령어
 * 
 *   nohup java -jar 파일명.jar &
 *   
 *   SSH창을 닫고 나서 url을 이용해서 접속 시도!
 *   그 로그값들은 libs 폴더 안에 .jar파일이랑 같이
 *   있다! 기본적인 이름 nohup.out 
 *   
 *   cat nohup.out 파일을 봐도 되지만 너무 길다
 *   
 *   뒤에서 부터 10개 확인 할 수있도록 
 *   tail -f nohup.out
 *   
 *   -f 실시간으로 로그 값을 출력해주는 옵션!
 *   
 *   ----------------------------------
 *   백그라운드에서 동작하는 프로세스들은 pid 부여된다.
 *   내가 종료를 누르고 싶을 경우에는 pid를 찾아서 
 *   kill -9 pid
 *   
 *   ps -fd
 *   
 *   로그 파일을 기본 파일이 아닌 다른 파일로 저장하는
 *   내용을 표준 입출력!
 * 
 */






